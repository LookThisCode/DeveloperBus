package main;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.api.services.plus.model.Person;
import com.google.gson.annotations.Expose;

import Model.DirectedUserToUserEdge;
import Model.User;
import Model.Jsonifiable;

public class ConnectServlet extends JsonRestServlet {
	
	/**
	 * Expuesto como `POST /api/connect`.
	 *
	 * Incluye la siguiente carga útil en el cuerpo de la solicitud.  La carga útil representa todos los
	 * parámetros necesarios para autorizar o conectar.
	 * {
	 *   "state":"",
	 *   "access_token":"",
	 *   "token_type":"",
	 *   "expires_in":"",
	 *   "code":"",
	 *   "id_token":"",
	 *   "authuser":"",
	 *   "session_state":"",
	 *   "prompt":"",
	 *   "client_id":"",
	 *   "scope":"",
	 *   "g_user_cookie_policy":"",
	 *   "cookie_policy":"",
	 *   "issued_at":"",
	 *   "expires_at":"",
	 *   "g-oauth-window":""
	 * }
	 *
	 * Devuelve la respuesta JSON siguiente representando al usuario que estaba
	 * conectado:
	 * {
	 *   "id":0,
	 *   "googleUserId":"",
	 *   "googleDisplayName":"",
	 *   "googlePublicProfileUrl":"",
	 *   "googlePublicProfilePhotoUrl":"",
	 *   "googleExpiresAt":0
	 * }
	 *
	 *Emite los siguientes errores junto con los códigos de respuesta HTTP correspondientes:
	 * 401: error del punto de destino de verificación del token.
	 * 500: "Error al actualizar el código de autorización". (para procesos de intercambio de código)
	 * 500: "Error al leer el token de datos de Google."
	 *      + error de la lectura de la respuesta de verificación del token.
	 * 500: "Error al consultar el API de Google+ API: " + error de la biblioteca del cliente.
	 * 500: Ha ocurrido una excepción IO (puede ocurrir por diversos motivos).
	 *
	 * @see javax.servlet.http.HttpServlet#doPost(
	 *     javax.servlet.http.HttpServletRequest,
	 *     javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
	  TokenData accessToken = null;
	  try {
	    // lee el token
	    accessToken = Jsonifiable.fromJson(req.getReader(), TokenData.class);
	  } catch (IOException e) {
	    sendError(resp, 400, "Unable to read auth result from request body");
	  }
	  // Crea un objeto credencial.
	  GoogleCredential credential = new GoogleCredential.Builder()
	      .setJsonFactory(JSON_FACTORY).setTransport(TRANSPORT)
	      .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
	  try {
	    if (accessToken.code != null) {
	      // cambia el código por un token (Frontend web)
	      GoogleTokenResponse tokenFromExchange = exchangeCode(accessToken);
	      credential.setFromTokenResponse(tokenFromExchange);
	    } else {
	      // utiliza el token recibido del cliente
	      credential.setAccessToken(accessToken.access_token)
	          .setRefreshToken(accessToken.refresh_token)
	          .setExpiresInSeconds(accessToken.expires_in)
	          .setExpirationTimeMilliseconds(accessToken.expires_at);
	    }
	    // asegúrate que consideramos al usuario propietario del código de acceso que ha iniciado sesión
	    String tokenGoogleUserId = verifyToken(credential);
	    User user = saveTokenForUser(tokenGoogleUserId, credential);
	    // guarda el usuario en la sesión
	    HttpSession session = req.getSession();
	    session.setAttribute(CURRENT_USER_SESSION_KEY, user.id);
	    generateFriends(user, credential);
	    sendResponse(resp, user);
	  } catch (TokenVerificationException e) {
	    sendError(resp, 401, e.getMessage());
	  } catch (TokenResponseException e) {
	    sendError(resp, 500, "Failed to upgrade the authorization code.");
	  } catch (TokenDataException e) {
	    sendError(resp, 500,
	        "Failed to read token data from Google. " + e.getMessage());
	  } catch (IOException e) {
	    sendError(resp, 500, e.getMessage());
	  } catch (GoogleApiException e) {
	    sendError(resp, 500, "Failed to query the Google+ API: " + e.getMessage());
	  }
	}
}