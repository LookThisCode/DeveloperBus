package jellybean_logistic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.*;

public class SetPositionServlet extends HttpServlet {
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try {
	    	Integer id = Integer.parseInt(request.getParameter("id"));
	    	double lat = Double.parseDouble(request.getParameter("lat"));
	    	double lng = Double.parseDouble(request.getParameter("lng"));
	    	boolean alert = Boolean.parseBoolean(request.getParameter("alert"));
	    	
	    	PrintWriter out = response.getWriter();
	    	
	    	Persistance.getInstance().setPosition(id, lat, lng, alert);
    	}
    	catch(Exception e) {
    		return;
    	}
    }
	
}
