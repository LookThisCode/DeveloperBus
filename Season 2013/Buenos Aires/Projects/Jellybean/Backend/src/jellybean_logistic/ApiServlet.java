package jellybean_logistic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.*;

public class ApiServlet extends HttpServlet {

	private static final String WEB_GETROUTES = "WEB_GETROUTES";
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        PrintWriter out = response.getWriter();
        
        out.println("Av Corrientes 2000, Buenos Aires, Ciudad Autónoma de Buenos Aires, Argentina");

        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		
    }
    
    public String processRequest(String op, String data) {
    	
    	Gson gson = new Gson();    	
		HashMap<String, String> params = null;
    	
		if (data != null && data != "")
			params = gson.fromJson(data, HashMap.class);
		
    	if (op == WEB_GETROUTES) {
    		
    		int id = Integer.parseInt(params.get("id"));
    		
    		
    		
    	}
    	
    	return null;
    }
    
    /*
    public Gson GetRoutes(int transport)
    {
    	Gson gson = new Gson();
    }
    */
    
    public static Map<Integer, Route> mRoutes;
    
    public static Map<Integer, Transport> mTransports;
    
    public void Initialize()
    {
    	/*
    	mRoutes = new HashMap<Integer, Route>();
    	mTransports = new HashMap<Integer, Transport>();
    	
    	// Transport 1
    	Transport tr = new Transport();
    	tr.Id = 1;
    	tr.mName = "Camión Volkswagen";
    	mTransports.put(1, tr);
    	
    	Route route = new Route();
    	route.mTransports = tr;
    	route.mDirections = new ArrayList<String>();
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");
    	mRoutes.put(1, route);
    	
    	// Transport 2
    	tr = new Transport();
    	tr.Id = 2;
    	tr.mName = "Camión Renault";
    	mTransports.put(2, tr);
    	
    	route = new Route();
    	route.mTransports = tr;
    	route.mDirections = new ArrayList<String>();
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");
    	mRoutes.put(2, route);
    	
    	// Transport 3
    	tr = new Transport();
    	tr.Id = 3;
    	tr.mName = "Camioneta Kangoo";
    	mTransports.put(3, tr);
    	
    	route = new Route();
    	route.mTransports = tr;
    	route.mDirections = new ArrayList<String>();
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");
    	mRoutes.put(3, route);
    	
    	// Transport 4
    	tr = new Transport();
    	tr.Id = 4;
    	tr.mName = "Camioneta Fiorino";
    	mTransports.put(4, tr);
    	
    	route = new Route();
    	route.mTransports = tr;
    	route.mDirections = new ArrayList<String>();
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");
    	route.mDirections.add("");    	
    	mRoutes.put(4, route);
    	*/
    }
    
}
