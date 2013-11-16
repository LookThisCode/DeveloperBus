package main;

import static Model.OfyService.ofy;

import Model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UsersServlet extends JsonRestServlet {
 
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      checkAuthorization(req);
      Long currentUserId = (Long) req.getSession().getAttribute(
          CURRENT_USER_SESSION_KEY);
      User user = ofy().load().type(User.class).id(currentUserId).get();
      sendResponse(req, resp, user);
    } catch (UserNotAuthorizedException e) {
      sendError(resp, 401, "Unauthorized request");
    }
  }
}
