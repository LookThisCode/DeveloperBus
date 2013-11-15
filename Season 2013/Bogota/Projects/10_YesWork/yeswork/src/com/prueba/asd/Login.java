package com.prueba.asd;


import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;



public class Login extends HttpServlet {
	

	/*public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();

		String thisURL = request.getRequestURI();
		if (request.getUserPrincipal() != null) {
			response.getWriter().println(
					"<p>Hello, " + request.getUserPrincipal().getName()
							+ "!  You can <a href=\""
							+ userService.createLogoutURL(thisURL)
							+ "\">sign out</a>.</p>");
		} else {
			response.getWriter().println(
					"<p>Please <a href=\""
							+ userService.createLoginURL(thisURL)
							+ "\">sign in</a>.</p>");
		}
	}*/
	
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {
		  
		 
	  
	  UserService userService = UserServiceFactory.getUserService(); User user
 = userService.getCurrentUser();
	  
	  if (user != null) { resp.setContentType("text/plain");
	  resp.getWriter().println("Bienvenido, " + user.getNickname()); } else {
	  resp.sendRedirect(userService.createLoginURL(req.getRequestURI())); } }
	 

	  public void llenar_base ()
	  {
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  
		  Entity employee = new Entity("Employee");
		  employee.setProperty("firstName", "Antonio");
		  employee.setProperty("lastName", "Salieri");
		  Date hireDate = new Date();
		  employee.setProperty("hireDate", hireDate);
		  employee.setProperty("attendedHrTraining", true);
		  
		  datastore.put(employee);
		  
	}
	  
}



