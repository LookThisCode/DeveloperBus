package main;

import static Model.OfyService.ofy;
import Model.User;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides an API for retrieving the currently logged in user. This servlet
 * provides the /api/users end-point, and exposes the following operations:
 *
 *   GET /api/users
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class CompaniesServlet extends JsonRestServlet {
  /**
   * Exposed as `GET /api/users`.
   *
   * Returns the following JSON response representing the currently logged in
   * User.
   *
   * {
   *   "id":0,
   *   "googleUserId":"",
   *   "googleDisplayName":"",
   *   "googlePublicProfileUrl":"",
   *   "googlePublicProfilePhotoUrl":"",
   *   "googleExpiresAt":0
   * }
   *
   * Issues the following errors along with corresponding HTTP response codes:
   * 401: "Unauthorized request".  No user was connected.
   *
   * @see javax.servlet.http.HttpServlet#doGet(
   *     javax.servlet.http.HttpServletRequest,
   *     javax.servlet.http.HttpServletResponse)
   */
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
