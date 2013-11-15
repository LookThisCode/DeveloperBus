package com.example.rest;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Challenge extends Activity {
	
	String URL_REST_SERVICE = "http://192.168.216.246:8085/sample/rest/ciudad";
	Httppostaux post;
    boolean result_back;
    private ProgressDialog pDialog;
    private Button boton;
    public TextView texto;
	JSONObject json_servicios;
	String respuesta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		post = new Httppostaux();
		setContentView(R.layout.activity_challenge);
		texto = (TextView) findViewById(R.id.txt_hola);
		boton = (Button) findViewById(R.id.btn_cargar);
		boton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 //Log.i("Click: ", "Hola Android.");
				 new asyncload().execute("");
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.challenge, menu);
		return true;
	}
	
	public void convertirDatos(){
    	try {
    		
	    	JSONArray lista = new JSONArray(respuesta);
	    	
	    	String datos = "";
	    	
	    	for (int i = 0; i < lista.length(); i++) {
	    		
	    		JSONObject jsObject = lista.getJSONObject(i);  
	    	    String id_city = jsObject.getString("id");
	    	    String name_city = jsObject.getString("nombre"); 
	    	    datos = datos.concat(id_city+":"+name_city+" - ");
	    	    Log.i("Ciudad: ",name_city);
	    	}
	    	Log.i("Datos", datos);
	    	texto.setText(datos);
    	} 
    	catch (JSONException e) {
    		Log.i("Error: ", "JSON Exception");
			e.printStackTrace();
		}
    }
    
    public void err_services(){
    	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Hubo un error cargando los datos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
    
public boolean loadStatus() {
    	 
    		ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();

    		//parametros.add(new BasicNameValuePair("var", "hola"));

			JSONObject jdata=post.getserverdata(parametros, URL_REST_SERVICE);
      		respuesta = post.getRespuesta();
      		//respuesta = "[{'id':'BO','nombre':'Bogotá'},{'id':'BU','nombre':'Buenos Aires'},{'id':'MX','nombre':'Mexico D.F.'},{'id':'SA','nombre':'Sao Paulo'}]";
    		
    		Log.i("RESPUESTA: ", respuesta);
		    		
		    if (respuesta !=null){
		    	jdata = null;
		    		return true;
		    		 
		    }else{	
		    			 Log.e("JSON METHOD: ", "Verificar Web Service.");
			    		return false;
			}
    	
    }

        class asyncload extends AsyncTask< String, String, String > {
        	 
        	
            protected void onPreExecute() {
            	
                pDialog = new ProgressDialog(Challenge.this);
                pDialog.setMessage("Cargando datos....");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }
     
    		protected String doInBackground(String... params) {
    			if (loadStatus()==true){    		    		
        			return "ok"; 
        		}else{    		
        			return "err";      	          	  
        		}
    		}
           
            protected void onPostExecute(String result) {

               pDialog.dismiss();
               Log.e("onPostExecute=",""+result);
               
               if (result.equals("ok")){
            	   
            	   convertirDatos();
    				
                }else{
                	err_services();
                }
                
            }
    		
        }

}
