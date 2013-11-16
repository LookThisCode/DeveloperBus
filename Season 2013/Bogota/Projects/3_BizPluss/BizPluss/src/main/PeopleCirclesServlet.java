package main;

import static Model.OfyService.ofy;

import Model.DirectedUserToUserEdge;
import Model.User;

import com.googlecode.objectify.Key;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PeopleCirclesServlet extends JsonRestServlet{
	
	 @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
	    try {
	      checkAuthorization(req);
	      String userId = req.getSession()
	            .getAttribute(CURRENT_USER_SESSION_KEY).toString();
	      User user = ofy().load().key(User.key(
	          Long.parseLong(userId))).safeGet();
	      List<DirectedUserToUserEdge> edges = ofy().load()
	          .type(DirectedUserToUserEdge.class)
	          .filter("peopleCirclesId =", user.getId()).list();
	      List<Key<User>> peopleCircles = new ArrayList<Key<User>>();
	      for (DirectedUserToUserEdge edge : edges) {
	    	  peopleCircles.add(User.key(edge.getFriendUserId()));
	      }
	      
	      Collection<User> circles = ofy().load().keys(peopleCircles).values();	      
	      sendResponse(req, resp, circles, "bizplus#circles");
	      
	    } catch (UserNotAuthorizedException e) {
	      sendError(resp, 401, "Unauthorized request");
	    }
	  }
}
