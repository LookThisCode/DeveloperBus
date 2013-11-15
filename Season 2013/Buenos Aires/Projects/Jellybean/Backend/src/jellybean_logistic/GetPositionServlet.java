package jellybean_logistic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.LatLng;

public class GetPositionServlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try {
	    	Integer id = Integer.parseInt(request.getParameter("id"));
	    	
	    	response.setCharacterEncoding("utf-8");
	    	PrintWriter out = response.getWriter();
	    	
	    	LatLng position = Persistance.getInstance().getPosition(id);
	    	
	    	Gson gson = new Gson();
	    	String json = gson.toJson(position);
	    	
	    	out.println(json);
	    	out.flush();
	        out.close();
    	}
    	catch (Exception e) {
    		return;
    	}
    }
    
}
