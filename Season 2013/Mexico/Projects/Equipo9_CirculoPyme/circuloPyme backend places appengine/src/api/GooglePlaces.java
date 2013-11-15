package api;

import bean.Places;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlaces {
	
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String GEOCODE_API_BASE = "http://maps.googleapis.com/maps/api/geocode";
    private static final String TYPE_NEARBYSEARCH = "/nearbysearch";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_TEXTSEARCH = "/textsearch";
    
    private static final String TYPE_DETAILS = "/details";
    private static final String OUT_JSON = "/json";
    
    //private String KEY = "AIzaSyB8CJh955GhUl8ZPVV513NVoPGY0pxPiYk";
    //private String browserKEY = "AIzaSyDFdvG2zTEl8PTkw4hQoU2QHmNicF-NPSU";
    private String KEY = "AIzaSyDF8WwY3WIz21fKDP7C7vFu29bN0OFQyDU";
    private String browserKEY = "AIzaSyCPVHrZzrf5bncgmQ-0F45pyqduBGaczW4";
    
    private String reference;
    private String query;
    
	public GooglePlaces(){
	}
	
	public Places details() {
		
		HttpURLConnection conn = null;
	    Places place = null;
	    String result = "";
	    StringBuilder url = new StringBuilder();
	    
	    url.append(PLACES_API_BASE);
	    url.append(TYPE_DETAILS);
	    url.append(OUT_JSON);
	    url.append("?sensor=false");
	    url.append("&key=" + KEY);
	    try{
	    url.append("&reference=" + URLEncoder.encode(getReference(), "utf8"));
	    }catch(Exception e){
	        e.printStackTrace();    
	    }
	    try{
	    URL urlRequest = new URL(url.toString());
	    conn = (HttpURLConnection) urlRequest.openConnection();
	    InputStream input = conn.getInputStream();
	    result = IOUtils.toString(input,"utf8");
	    }catch (MalformedURLException e) {
            return place;
        }
	    catch (IOException e) {
            return place;
        }
	    finally {
	    	if (conn != null) {
                conn.disconnect();
            }
	    }
	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(result.toString()).getJSONObject("result");
	
	        place = new Places();
	        place.setName(jsonObj.getString("name"));
	        place.setFormatAddress(jsonObj.getString("formatted_address"));
	        JSONObject jsonGeo = (JSONObject) jsonObj.get("geometry");
	        JSONObject jsonLoc = (JSONObject) jsonGeo.get("location");
	        place.setLatitud(jsonLoc.getDouble("lat")+"");
	        place.setLongitude(jsonLoc.getDouble("lng")+"");
	        try{
		        place.setUrl(URLDecoder.decode(jsonObj.getString("url"), "UTF-8"));
	        	place.setWebsite(URLDecoder.decode(jsonObj.getString("website"), "UTF-8"));
		    }catch(Exception e){
		        e.printStackTrace();    
		    }
	        
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	
	    return place;
	}
	
public ArrayList<Places> textSearch() {
        
		HttpURLConnection conn = null;
        ArrayList<Places> resultList = null;
        String result = "";
        StringBuilder url = null;
        boolean hasPagetoken = false;
        String pagetoken = "";
        ArrayList<String> urlsToReturn = null;        
        do{ 
            url = new StringBuilder("");
            url.append(PLACES_API_BASE);
            url.append(TYPE_TEXTSEARCH);
            url.append(OUT_JSON);
            url.append("?sensor=false");
            url.append("&key=" + KEY);
            if(hasPagetoken){
                url.append("&pagetoken="+pagetoken);
            }else{
                try{
                	url.append("&query=" + URLEncoder.encode(getQuery(), "utf8"));
                }catch(Exception e){
                    e.printStackTrace();    
                }
            }
 
            try{
        	    URL urlRequest = new URL(url.toString());
        	    conn = (HttpURLConnection) urlRequest.openConnection();
        	    InputStream input = conn.getInputStream();
        	    result = IOUtils.toString(input,"utf8");
        	    }catch (MalformedURLException e) {
        	    	e.printStackTrace();
                }
        	    catch (IOException e) {
        	    	e.printStackTrace();
                }
        	    finally {
        	    	if (conn != null) {
                        conn.disconnect();
                    }
        	    }
    
            try {
                // Create a JSON object hierarchy from the results
                JSONObject jsonObj = new JSONObject(result.toString());
                JSONArray predsJsonArray = jsonObj.getJSONArray("results");
                
                // Extract the Place descriptions from the results
                if(!hasPagetoken)
                resultList = new ArrayList<Places>(predsJsonArray.length());
                /*
                if(jsonObj.has("next_page_token"))
                {
                    pagetoken = jsonObj.getString("next_page_token");
                    hasPagetoken = true;
                    try{
                    Thread.sleep(4000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    hasPagetoken = false;
                    
                }
                */
                for (int i = 0; i < (predsJsonArray.length()<=5?predsJsonArray.length():5); i++) {
                    
                    Places place = new Places();
                    place.setReference(predsJsonArray.getJSONObject(i).getString("reference"));
                    place.setName(predsJsonArray.getJSONObject(i).getString("name"));
                    place.setFormatAddress(predsJsonArray.getJSONObject(i).getString("formatted_address"));
                    JSONObject jsonGeo = (JSONObject) predsJsonArray.getJSONObject(i).get("geometry");
                    JSONObject jsonLoc = (JSONObject) jsonGeo.get("location");
                    place.setLatitud(jsonLoc.getDouble("lat")+"");
                    place.setLongitude(jsonLoc.getDouble("lng")+"");
                    urlsToReturn = returnUrlWebsiteSearch(place.getReference());
                    place.setUrl(urlsToReturn.get(0));
                    place.setWebsite(urlsToReturn.get(1));
                    try{
                    JSONArray jsonPhoto  = (JSONArray) predsJsonArray.getJSONObject(i).getJSONArray("photos");
                    if(jsonPhoto.length()>0)
                    	place.setUrlPhoto("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+jsonPhoto.getJSONObject(i).getString("photo_reference")+"&sensor=true&key="+browserKEY);
                    }catch(JSONException e){
                    	place.setUrlPhoto("Url No Disponible");
                    }
                    resultList.add(place);
                }
                
            } catch (JSONException e) {
                e.printStackTrace();    
            }
        }while(hasPagetoken);
        
        return resultList;
    }
	
public ArrayList<String> returnUrlWebsiteSearch(String reference) {
		
		HttpURLConnection conn = null;
		ArrayList<String> urlLists = new ArrayList<String>();
		String urlToReturn = "Url no Disponible";
		String websiteToReturn = "Url no Disponible";
	    
	    String result = "";
	    StringBuilder url = new StringBuilder();
	    
	    url.append(PLACES_API_BASE);
	    url.append(TYPE_DETAILS);
	    url.append(OUT_JSON);
	    url.append("?sensor=false");
	    url.append("&key=" + KEY);
	    try{
	    url.append("&reference=" + reference);
	    }catch(Exception e){
	        e.printStackTrace();    
	    }
	    try{
	    URL urlRequest = new URL(url.toString());
	    conn = (HttpURLConnection) urlRequest.openConnection();
	    InputStream input = conn.getInputStream();
	    result = IOUtils.toString(input,"utf8");
	    }catch (MalformedURLException e) {
        }
	    catch (IOException e) {
        }
	    finally {
	    	if (conn != null) {
                conn.disconnect();
            }
	    }
	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(result.toString()).getJSONObject("result");
	        try{
	        	urlToReturn = URLDecoder.decode(jsonObj.getString("url"), "UTF-8");
	        	websiteToReturn = URLDecoder.decode(jsonObj.getString("website"), "UTF-8");
    	    }catch(Exception e){
    	        e.printStackTrace();    
    	    }
	        
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	    urlLists.add(urlToReturn);
	    urlLists.add(websiteToReturn);
	    
	    return urlLists;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}

}
