package mx.stockin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class StockinDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = -2270680011229621357L;

	@Override
	protected void doDispatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if (user != null) {
        	
        	request.getSession().setAttribute("nickname", user.getNickname());
        	request.getSession().setAttribute("gUser", user);
        	
    		super.doDispatch(request, response);
        } else {
        	response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
        }
	}
}
