package jellybean_logistic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHomePositionServlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try {
    		response.setCharacterEncoding("utf-8");
	    	PrintWriter out = response.getWriter();
	    	
	    	//out.println("{ \"address\" : \"Corrientes 2000, Buenos Aires, Ciudad Autonoma de Buenos Aires, Argentina\" }");
	    	//out.println("{ \"address\" : \"Bucarelli 1581, Buenos Aires, Ciudad Autonoma de Buenos Aires, Argentina\" }");
	    	out.println("{ \"address\":\"El Boyero 1749, Pilar, Buenos Aires, Argentina\" }");
	    	
	    	out.flush();
	    	
	        out.close();
    	}
    	catch(Exception e) {
    		return;
    	}
    }
    
}
