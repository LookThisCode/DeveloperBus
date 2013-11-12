package com.pymecircle.places;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import api.GooglePlaces;
import com.google.gson.*;
import bean.Places;
import bean.PlacesList;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Pyme_Circle_APIServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    private static final String CONTENT_TYPE_JSONP = "application/x-javascript; charset=utf-8";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if(request.getParameter("peticion").equals("details")){
	            
            String reference = request.getParameter("reference");
            
            GooglePlaces gPlaces = new GooglePlaces();
            gPlaces.setReference(reference);
            Places  place = gPlaces.details();
               
            request.setCharacterEncoding("utf8");
            response.setContentType(CONTENT_TYPE_JSONP);
            PrintWriter out = response.getWriter();
            
            Gson gson = new Gson();
            String json = gson.toJson(place);
            
            out.print(request.getParameter("callback")+"("+json+")");
            out.flush();
            out.close();
	            
		}else if(request.getParameter("peticion").equals("textSearch")){
			
			String query = request.getParameter("query");
			GooglePlaces gPlaces = new GooglePlaces();
            gPlaces.setQuery(query);
            
            ArrayList<Places>  placesList = gPlaces.textSearch();
            PlacesList pList = new PlacesList();
            pList.setPlacesList(placesList);
            
            request.setCharacterEncoding("utf8");
            response.setContentType(CONTENT_TYPE_JSONP);
            PrintWriter out = response.getWriter();
            
            Gson gson = new Gson();
            String json = gson.toJson(pList);
            
            out.print(request.getParameter("callback")+"("+json+")");
            out.flush();
            out.close();	
		}
	}
}
