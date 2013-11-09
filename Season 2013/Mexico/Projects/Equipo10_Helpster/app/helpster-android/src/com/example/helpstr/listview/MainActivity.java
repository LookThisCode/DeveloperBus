package com.example.helpstr.listview;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import static us.monoid.web.Resty.*;
import us.monoid.web.Resty;
import us.monoid.web.Resty.*;


import com.example.helpstr.listview.library.JSONParser;

public class MainActivity extends Activity {
	ListView list;
	TextView ver;
	TextView name;
	TextView api;
	TextView prub;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	//URL to get JSON Array
	private static String url = "http://welod.com/ejemplo.json";

	
	//JSON Node Names 
	private static final String TAG_OS = "android";
	private static final String TAG_VER = "ver";
	private static final String TAG_NAME = "name";
	private static final String TAG_API = "api";
	private static final String TAG_PRUEBA = "prueba";
	
	JSONArray android = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.activity_main);
        oslist = new ArrayList<HashMap<String, String>>();

        
        
        Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new JSONParse().execute();
			}
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
    	 private ProgressDialog pDialog;
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
             ver = (TextView)findViewById(R.id.vers);
			 name = (TextView)findViewById(R.id.name);
			 api = (TextView)findViewById(R.id.api);
			 prub = (TextView)findViewById(R.id.prueba);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
    	}
		Resty r = new Resty();
		public String hello = "http://helpster-api.appspot.com/v1/alertas"; 
    	@Override
        protected JSONObject doInBackground(String... args) {
    		
    		JSONParser jParser = new JSONParser();

    		// Getting JSON from URL
    		
   		
    		JSONObject json = jParser.getJSONFromUrl(url);
    		return json;
    	}
    	 @Override
         protected void onPostExecute(JSONObject json) {
    		 pDialog.dismiss();
    		 try {
    				// Getting JSON Array from URL
    				android = json.getJSONArray(TAG_OS);
    				for(int i = 0; i < android.length(); i++){
    				JSONObject c = android.getJSONObject(i);
    				
    				// Storing  JSON item in a Variable
    				String ver = c.getString(TAG_VER);
    				String name = c.getString(TAG_NAME);
    				String api = c.getString(TAG_API);
    				String prub = c.getString(TAG_PRUEBA);
    				
    			
    				
    				
    				// Adding value HashMap key => value
    				

    				HashMap<String, String> map = new HashMap<String, String>();

    				map.put(TAG_VER, ver);
    				map.put(TAG_NAME, name);
    				map.put(TAG_API, api);
    				map.put(TAG_PRUEBA, prub);
    				
    				oslist.add(map);
    				list=(ListView)findViewById(R.id.list);
    				
    				
    				
    		        
    				
    				ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
    						R.layout.list_v,
    						new String[] { TAG_VER,TAG_NAME, TAG_API, TAG_PRUEBA }, new int[] {
    								R.id.vers,R.id.name, R.id.api, R.id.prueba});

    				list.setAdapter(adapter);
    				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    		            @Override
    		            public void onItemClick(AdapterView<?> parent, View view,
    		                                    int position, long id) {
    		                Toast.makeText(MainActivity.this, "You Clicked at "+oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

    		            }
    		        });

    				}
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}

    		 
    	 }
    }
    
}
