package main;

import static Model.OfyService.ofy;

import com.google.api.client.http.GenericUrl;
import main.JsonRestServlet.UserNotAuthorizedException;
import Model.Message;

import Model.DirectedUserToUserEdge;
import Model.User;
import Model.Company;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class DisconnectServlet extends JsonRestServlet {
  

	  @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	      checkAuthorization(req);
	      Long userId = Long.parseLong(req.getSession()
	          .getAttribute(CURRENT_USER_SESSION_KEY).toString());
	      List<DirectedUserToUserEdge> edges = ofy().load()
	          .type(DirectedUserToUserEdge.class)
	          .filter("ownerUserId", userId).list();
	      ofy().delete().entities(edges);
	      
	      List<Company> companyPlus = ofy().load().type(Company.class)
	          .filter("peoplePlusId", userId).list();
	      ofy().delete().entities(companyPlus);
	      
	      List<Company> companyCircles = ofy().load().type(Company.class)
		          .filter("peopleCirclesId", userId).list();
		      ofy().delete().entities(companyCircles);
		      
	      User user = ofy().load().type(User.class).id(userId).get();
	      ofy().delete().entity(user);

	      revokeToken(user.getGoogleAccessToken());

	      req.getSession().removeAttribute(CURRENT_USER_SESSION_KEY);
	      sendResponse(req, resp, new Message("Successfully disconnected."),
	          "message");
	    } catch (UserNotAuthorizedException e) {
	      sendError(resp, 401,
	          "Unauthorized request");
	    } catch (IOException e) {
	      sendError(resp, 500,
	          "Failed to revoke token for given user: " + e.getMessage());
	    }
	  }
 
  protected static void revokeToken(String accessToken) throws IOException {
    TRANSPORT.createRequestFactory().buildGetRequest(new GenericUrl(
        String.format(
            "https://accounts.google.com/o/oauth2/revoke?token=%s",
            accessToken))).execute();
  }
}