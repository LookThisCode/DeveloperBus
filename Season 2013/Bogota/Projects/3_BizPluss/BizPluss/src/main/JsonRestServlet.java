

package main;
import static Model.OfyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;


import Model.Jsonifiable;
import Model.User;


public abstract class JsonRestServlet extends HttpServlet {
  
  public static final String CLIENT_ID = "654816093631-k1s4jfouqv3bvsq8ui6qh5uc2mq18nqp.apps.googleusercontent.com";

  public static final String CLIENT_SECRET = "azxLisHxadUgcrAsTRNKAwcr";

  public static final String JSON_MIMETYPE = "application/json";

  public static final String CURRENT_USER_SESSION_KEY = "me";

  public static final JsonFactory JSON_FACTORY = new JacksonFactory();

  public static final HttpTransport TRANSPORT = new NetHttpTransport();

  private static final Long HUNDRED_SECONDS_IN_MS = 100000l;

  
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

  protected void checkAuthorization(HttpServletRequest req)
      throws UserNotAuthorizedException {
    HttpSession session = req.getSession();

    if (session == null
        || session.getAttribute(CURRENT_USER_SESSION_KEY) == null) {
      throw new UserNotAuthorizedException();
    }
  }

  protected GoogleCredential getCredentialFromLoggedInUser(
      HttpServletRequest req) throws GoogleTokenExpirationException {
    User loggedInUser = ofy()
        .load()
        .type(User.class)
        .id(Long.parseLong(req.getSession()
            .getAttribute(CURRENT_USER_SESSION_KEY).toString()))
        .get();

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

  public class UserNotAuthorizedException extends Exception {
  }

  public class GoogleTokenExpirationException extends Exception {
  }

}
