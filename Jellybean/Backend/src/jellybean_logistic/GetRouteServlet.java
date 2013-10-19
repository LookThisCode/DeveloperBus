package jellybean_logistic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.*;

public class GetRouteServlet extends HttpServlet {
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try {
    		
    		String str = request.getParameter("id");
    		
    		Integer id = Integer.parseInt(str);
    		
    		response.setCharacterEncoding("utf-8");
	    	PrintWriter out = response.getWriter();
	    	
	    	Route route = Persistance.getInstance().getRoute(id);
	    	
	    	Gson gson = new Gson();
	    	String json = gson.toJson(route);
	    	
	    	out.println(json);
	    	out.flush();
	        out.close();
    	}
    	catch(Exception e){
    		return ;
    	}
    }
	
}
