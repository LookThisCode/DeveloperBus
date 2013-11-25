package com.alow.servlet;

import static com.alow.dao.ofy.OfyService.ofy;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alow.model.plus.Jsonifiable;
import com.alow.model.plus.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * Abstract servlet to be used by those servlets implementing a JSON ReST API,
 * or at least part of one. Abstracts out a lot of common logic, and makes it
 * easy to send responses to requests.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public abstract class JsonRestServlet extends HttpServlet {
  
	private static final long serialVersionUID = -9051269480064294710L;

/**
   * Replace this with the client ID you got from the Google APIs console.
   */
  public static final String CLIENT_ID = "937796345609.apps.googleusercontent.com";

  /**
   * Replace this with the client secret you got from the Google APIs console.
   */
  public static final String CLIENT_SECRET = "oOeQSbOUGZLkjdyYhOKLkMA1";

  /**
   * MIME type to use when sending responses back to PhotoHunt clients.
   */
  public static final String JSON_MIMETYPE = "application/json";

  /**
   * Key name in the session referring to the Google user ID of the current
   * user.
   */
  public static final String CURRENT_USER_SESSION_KEY = "me";

  /**
   * JsonFactory to use in parsing JSON.
   */
  public static final JsonFactory JSON_FACTORY = new JacksonFactory();

  /**
   * HttpTransport to use for external requests.
   */
  public static final HttpTransport TRANSPORT = new NetHttpTransport();

  /**
   * 100 seconds in milliseconds for token expiration calculations.
   */
  private static final Long HUNDRED_SECONDS_IN_MS = 100000l;

  /**
   * Send an error down the given response.
   *
   * @param resp Response to use in transmitting error.
   * @param code HTTP response code to issue.
   * @param message Message to attach to response.
   */
  protected void sendError(HttpServletResponse resp, int code, String message) {
    try {
      if (code == 401) {
        resp.addHeader("WWW-Authenticate",
            "OAuth realm=\"PhotoHunt\", error=\"invalid-token\"");
      }

      resp.sendError(code, message);
    } catch (IOException e) {
        throw new RuntimeException(message);
      }
  }

  /**
   * Send the given object (via body.toString()) down the given response.
   *
   * Attempts to send an HTTP 500 if there was an error in writing the
   * response.
   *
   * @param resp Response to use in transmitting body.
   * @param body Object on which to call toString() to generate response.
   */
  protected void sendResponse(HttpServletRequest req,
      HttpServletResponse resp, Collection<? extends Jsonifiable> body,
      String kind) {
    resp.setContentType(JSON_MIMETYPE);
    try {
      if (req.getParameter("items") != null) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        jsonObject.put("kind", kind);
        jsonObject.put("items", body);
        resp.getWriter().print(Jsonifiable.GSON.toJson(jsonObject));
      } else {
        resp.getWriter().print(Jsonifiable.GSON.toJson(body));
      }
    } catch (IOException e) {
      sendError(
          resp,
          500,
          new StringBuffer()
              .append("Servlet received an IOException trying to write response ")
              .append("body to HttpServletResponse.").toString());
    }
  }

  /**
   * Send the given object (via body.toString()) down the given response.
   *
   * Attempts to send an HTTP 500 if there was an error in writing the
   * response.
   *
   * @param resp Response to use in transmitting body.
   * @param body Object on which to call toString() to generate response.
   */
  protected void sendResponse(HttpServletRequest req,
      HttpServletResponse resp, Object body, String kind) {
    resp.setContentType(JSON_MIMETYPE);
    try {
      if (req.getParameter("items") != null) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        jsonObject.put("kind", kind);
        jsonObject.put("item", body);
        resp.getWriter().print(Jsonifiable.GSON.toJson(jsonObject));
      } else {
        resp.getWriter().print(body.toString());
      }
    } catch (IOException e) {
      sendError(
          resp,
          500,
          new StringBuffer()
              .append("Servlet received an IOException trying to write response ")
              .append("body to HttpServletResponse.").toString());
    }
  }

  /**
   * Send the given object (via body.toString()) down the given response.
   *
   * Attempts to send an HTTP 500 if there was an error in writing the
   * response.
   *
   * @param resp Response to use in transmitting body.
   * @param body Object on which to call toString() to generate response.
   */
  protected void sendResponse(HttpServletRequest req,
      HttpServletResponse resp, Jsonifiable body) {
    resp.setContentType(JSON_MIMETYPE);
    try {
      if (req.getParameter("items") != null) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        jsonObject.put("kind", body.kind);
        jsonObject.put("item", body);
        resp.getWriter().print(Jsonifiable.GSON.toJson(jsonObject));
      } else {
        resp.getWriter().print(body.toString());
      }
    } catch (IOException e) {
      sendError(
          resp,
          500,
          new StringBuffer()
              .append("Servlet received an IOException trying to write response ")
              .append("body to HttpServletResponse.").toString());
    }
  }

  /**
   * Ensure that there is a user connected before honoring the given request.
   *
   * @param req Request to validate by checking existence of session.
   * @throws UserNotAuthorizedException User does not have existing session.
   */
  protected void checkAuthorization(HttpServletRequest req)
      throws UserNotAuthorizedException {
    HttpSession session = req.getSession();

    if (session == null
        || session.getAttribute(CURRENT_USER_SESSION_KEY) == null) {
      throw new UserNotAuthorizedException();
    }
  }

  /**
   * @param req Request to query for session data.
   * @return Credential representing currently connected User.
   * @throws GoogleTokenExpirationException User's token is expired and cannot be used.
   */
  protected GoogleCredential getCredentialFromLoggedInUser(
      HttpServletRequest req) throws GoogleTokenExpirationException {
    User loggedInUser = ofy()
        .load()
        .type(User.class)
        .id(Long.parseLong(req.getSession()
            .getAttribute(CURRENT_USER_SESSION_KEY).toString()))
        .get();

    // If the user doesn't have a refresh token, check if the expiration of
    // the access token is near to signal to the client to get a new token.
    if (loggedInUser.getGoogleRefreshToken() == null) {
      Long now = new Date().getTime();
      if (now >= (loggedInUser.getGoogleExpiresAt() - HUNDRED_SECONDS_IN_MS)) {
        throw new GoogleTokenExpirationException();
      }
    }

    GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
        .build()
        .setAccessToken(loggedInUser.getGoogleAccessToken())
        .setRefreshToken(loggedInUser.getGoogleRefreshToken())
        .setExpirationTimeMilliseconds(loggedInUser.getGoogleExpiresAt());

    return credential;
  }

  /**
   * Thrown if the current user is not authorized or connected.
   */
  public class UserNotAuthorizedException extends Exception {
  }

  /**
   * Thrown if the current user's access token is expired and the user has no
   * refresh token stored.
   */
  public class GoogleTokenExpirationException extends Exception {
  }

}