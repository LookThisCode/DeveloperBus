package com.example.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class Httppostaux{
    
	  InputStream is = null;
	  String result = "";
	  
	  public JSONObject getserverdata(ArrayList<NameValuePair> parameters, String urlwebserver ){
		  
	  //Conecta via http y envia un post.
	  httppostconnect(parameters,urlwebserver);
		  
	  if (is!=null){
		  //si obtuvo una respuesta
		  getpostresponse();
		 return getjsonarray();
	  
	  }else{
		  
	      return null;

	  }
		  
	  }
	  
	   
	  //peticion HTTP
    private void httppostconnect(ArrayList<NameValuePair> parametros, String urlwebserver){
   	
  	//
  	try{
  	        HttpClient httpclient = new DefaultHttpClient();
  	        HttpPost httppost = new HttpPost(urlwebserver);
  	        httppost.setEntity(new UrlEncodedFormEntity(parametros));
  	        //ejecuto peticion enviando datos por POST
  	        HttpResponse response = httpclient.execute(httppost); 
  	        HttpEntity entity = response.getEntity();
  	         is = entity.getContent();
  	         
  	}catch(Exception e){
  	        Log.e("log_tag", "Error in http connection "+e.toString());
  	}
  	
  }
  
  public void getpostresponse(){
  
  	//Convierte respuesta a String
  	try{
  	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
  	        StringBuilder sb = new StringBuilder();
  	        String line = null;
  	        while ((line = reader.readLine()) != null) {
  	                sb.append(line + "\n");
  	        }
  	        is.close();
  	 
  	        result=sb.toString();
  	        Log.e("getpostresponse"," result= "+sb.toString());
  	}catch(Exception e){
  	        Log.e("log_tag", "Error converting result "+e.toString());
  	}
 }
  
  public JSONObject getjsonarray(){
	  
	  try {
          JSONObject jObj = new JSONObject(result);
          return jObj;
      } catch (JSONException e) {
          Log.e("JSON Parser", "Error parsing data " + e.toString());
          return null;
      }
	  
	  
  	//parse json data
  	/*try{
          JSONArray jArray = new JSONArray(result);
          
          return jArray;
  	}
  	catch(JSONException e){
  	        Log.e("log_tag", "Error parsing data "+e.toString());
  	        return null;
  	}*/
		
  }
  public String getRespuesta(){
	  return result;
  }
  	
  	
  }	