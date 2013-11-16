package com.devbus.stockin;
import java.io.IOException;
import java.util.logging.Logger;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class StockinServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(StockinServlet.class.getName());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        String catalogueName = req.getParameter("catalogueName");
        Key catalogueKey = KeyFactory.createKey("Catalogue", catalogueName);
        String description = req.getParameter("description");
        String category = req.getParameter("category");
        Date date = new Date();      
        
        Entity catalogue = new Entity("Catalogue", catalogueKey);
        catalogue.setProperty("user", user);
        catalogue.setProperty("date", date);
        catalogue.setProperty("cname", catalogueName);
        catalogue.setProperty("category", category);
        catalogue.setProperty("description", description);
      
        
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(catalogue);

        resp.sendRedirect("/catalogues.jsp?catalogueName=" + catalogueName);

        
        
       }
}
