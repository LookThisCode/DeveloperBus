package com.example.jellybean_logistic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import serializer.PlaceDeserializer;
import serializer.RouteDeserializer;
import serializer.TransportDeserializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import entities.Place;
import entities.Route;

public class MainActivity extends Activity implements LocationListener, OnClickListener {

	private String APPENGINE_ADDR_DESTINOS = "https://jellybean-logistic.appspot.com/api/getroute";
	private String APPENGINE_ADDR_POSITION = "https://jellybean-logistic.appspot.com/api/setposition";
	private String APPENGINE_ADDR_STATE = "https://jellybean-logistic.appspot.com/api/changestate";
	private int my_ID = 2;
	private HashMap<String, HashMap<String, String>> Destinos;
	private HashMap<String, List<Marker>> Markers = new HashMap<String, List<Marker>>();
	private String indice_destino_actual = null;
	
	private LatLng my_location = null;
	private LatLng last_requested_destination = null;
	Polyline rutaActual = null;
	private String last_opened_destination;
	
	private GoogleMap map;
	private LocationManager locationManager;
	private Spinner spinner;
	boolean skip_next_info = false;
	private CheckBox chkVisitado;
	private CheckBox chkAusente;

	Timer updateTimer = new Timer();
	Timer updatePositionTimer = new Timer();
	
	public boolean alert;
	ImageButton alert_button;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_main);
      
        spinner = (Spinner) findViewById(R.id.spinner);
        chkVisitado = (CheckBox) findViewById(R.id.chkVisitado);
        chkAusente = (CheckBox) findViewById(R.id.chkAusente);
        
        // Get a handle to the Map Fragment
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1000, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 400, 1000, this);
        Location lastknown = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        my_location = new LatLng(lastknown.getLatitude(), lastknown.getLongitude()); 
        
        LatLng buenosaires = new LatLng(-34.603, -58.381);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(buenosaires, 15));
        
        updatePositionTimer.schedule(new TimerTask() {
        	@Override
        	public void run() {
        		setPublicPosition();
        	}
        }, 0, 5000);
        
        getDestinos();
        
        /*
         * DEBUGUEAR ESTA COSA
        updateTimer.schedule(new TimerTask() {          
            @Override
            public void run() {
                updateCurrentRoute();
            }

        }, 0, 1000);
        */

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {

				String key = (String) parent.getItemAtPosition(pos);
				mostrarInformacion(key);
			}

			public void onNothingSelected(AdapterView<?> parent) {
								
			}
        });
        
        chkVisitado.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {

				if(isChecked)
				{
					cambiarEstado(indice_destino_actual, "Visitado");
					chkAusente.setChecked(false);
				}
				else if(chkAusente.isChecked() == false)
				{
					cambiarEstado(indice_destino_actual, "No Visitado");
				}
				
			}
        });
        
        chkAusente.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {

				if(isChecked)
				{
					cambiarEstado(indice_destino_actual, "Ausente");
					chkVisitado.setChecked(false);
				}
				else if(chkVisitado.isChecked() == false)
				{
					cambiarEstado(indice_destino_actual, "No Visitado");
				}
				
			}
        });
        
        alert = false;
        alert_button = (ImageButton) findViewById(R.id.alertButton);
        
        alert_button.setOnClickListener(new AlertButton(this));
    }

	public void onLocationChanged(Location location) {
		my_location = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(my_location, 15);
		map.animateCamera(cameraUpdate);
		locationManager.removeUpdates(this);
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	void mostrarInformacion(String key)
	{
		if(skip_next_info == true)
		{
			skip_next_info = false;
			return;
		}
		
		last_opened_destination = key;
		
		String data;
		data = key;
		data += "\nDirección: ";
		data += Destinos.get(key).get("direccion");
		data += "\nNotas: ";
		data += Destinos.get(key).get("notas");
		data += "\nEstado: ";

		if(Destinos.get(key).get("estado") == "No Visitado")
		{
			data += Html.fromHtml("<font color='#00FF00'><b>No Visitado</b></font>"); 
		}
		else if(Destinos.get(key).get("estado") == "Visitado")
		{
			data += Html.fromHtml("<font color='#0000AA'><b>Visitado</b></font>");
		}
		else if(Destinos.get(key).get("estado") == "Ausente")
		{
			data += Html.fromHtml("<font color='#FF0000'><b>Ausente</b></font>");
		}
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this)
		    .setTitle("Información de destino")
		    .setMessage(data)
		    .setPositiveButton("No Visitado", this)
		    .setNeutralButton("Visitado", this)
		    .setNegativeButton("Ausente", this);
		
		dialog.show();
	}
	
	public void getDestinos() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("id", Integer.toString(my_ID));
		AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
		asyncHttpPost.execute(APPENGINE_ADDR_DESTINOS);
		Log.w("POST", "Solicite posicion");
	}
	
	public void actualizarDestinos(HashMap<String, HashMap<String, String>> destinos)
	{
		Destinos = destinos;
		
		List<String> list = new ArrayList<String>();
		boolean primero = false;
		
		for(String key : Destinos.keySet())
		{
			String nombre = key;
			String address = Destinos.get(key).get("direccion");
			
			LatLng position = GetLatLngFromAddress(address);
			
			Log.w("Destino a poner marcador", nombre);
			Log.w("Lugar", address);
			Log.w("lat", Double.toString(position.latitude));
			Log.w("long", Double.toString(position.longitude));
			
			Marker visited = map.addMarker(new MarkerOptions()
		        .position(position)
		        .title(nombre)
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
			
			Marker notvisited = map.addMarker(new MarkerOptions()
		        .position(position)
		        .title(nombre)
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			
			Marker ausent = map.addMarker(new MarkerOptions()
		        .position(position)
		        .title(nombre)
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			
			List<Marker> marcadores = new ArrayList<Marker>();
			marcadores.add(visited);
			marcadores.add(notvisited);
			marcadores.add(ausent);
			Markers.put(key, marcadores);
			
			list.add(0, nombre); // Agregar adelante
			
			// if(Destinos.get(key).get("orden") == "0")
			if(primero == false)
			{
				primero = true;
				cambiarDestino(key);
			}
				
		}
		
		skip_next_info = true;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		
		actualizarCheckboxes();
		actualizarMarcadores();
	}
	
	public void cambiarDestino(String nombre)
	{
		Log.w("Debug", "cambiarDestino");
		
		indice_destino_actual = nombre;
		mostrarRuta(nombre, (String) Destinos.get(nombre).get("direccion"));
		spinner.setSelection(Integer.parseInt(Destinos.get(nombre).get("orden")), true);
		
		String estado = Destinos.get(nombre).get("estado");
		if(estado == "Visitado")
			chkVisitado.setChecked(true);
		else
			chkVisitado.setChecked(false);
		
		if(estado == "Ausente")
			chkAusente.setChecked(true);
		else
			chkAusente.setChecked(false);
	}
	
	public void mostrarRuta(String nombre, String address)
	{
		Log.w("Debug", "mostrarRuta");
		Log.w("Name", nombre);
		Log.w("Address", address);
		
		LatLng position = GetLatLngFromAddress(address);
		last_requested_destination = position;
		
		findDirections(my_location.latitude, my_location.longitude,
				position.latitude, position.longitude, GMapV2Direction.MODE_DRIVING, 0 );
	}
	
	public LatLng GetLatLngFromAddress(String address)
	{
		LatLng position = new LatLng(0.0, 0.0);
		try
		{
			List<Address> result = new Geocoder(this).getFromLocationName(address, 1);
			position = new LatLng(result.get(0).getLatitude(), result.get(0).getLongitude());
			Log.w("Position Latitude", Double.toString(result.get(0).getLatitude()));
			Log.w("Position Latitude", Double.toString(result.get(0).getLongitude()));
		}
		catch (IOException e) 
		{
	        e.printStackTrace();
	    }
		
		return position;
	}
	
	public void updateCurrentRoute()
	{
		if(last_requested_destination != null)
		{
			findDirections(my_location.latitude, my_location.longitude,
					last_requested_destination.latitude, last_requested_destination.longitude, GMapV2Direction.MODE_DRIVING,
					1);
		}
	}

	public class AsyncHttpPost extends AsyncTask<String, String, String> {
	    private HashMap<String, String> mData = null;// post data

	    /**
	     * constructor
	     */
	    public AsyncHttpPost(HashMap<String, String> data) {
	        mData = data;
	    }

	    /**
	     * background
	     */
	    @Override
	    protected String doInBackground(String... params) {
	        byte[] result = null;
	        String str = "";
	        HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(params[0]);// in this case, params[0] is URL
	        try {
	            // set up post data
	            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
	            Iterator<String> it = mData.keySet().iterator();
	            while (it.hasNext()) {
	                String key = it.next();
	                nameValuePair.add(new BasicNameValuePair(key, mData.get(key)));
	            }

	            post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
	            HttpResponse response = client.execute(post);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
	                result = EntityUtils.toByteArray(response.getEntity());
	                str = new String(result, "UTF-8");
	            }
	        }
	        catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        catch (Exception e) {
	        }
	        return str;
	    }

	    /**
	     * on getting result
	     */
	    @Override
	    protected void onPostExecute(String result) {
	    	/*
	    	HashMap<String, HashMap<String, String>> nuevos_destinos = new HashMap<String, HashMap<String, String>>();
	        
	        nuevos_destinos.put("Un lugar", new HashMap<String, String>(){
	        	{put("orden","0");
	        	put("direccion","Corrientes 5400, Buenos Aires, Argentina");
	        	put("notas","Un lugar sobre la calle Corrientes");
	        	put("estado","No Visitado");}
	        	});

	        actualizarDestinos(nuevos_destinos);
	        */
	       if(result == null || result == "" || result.length() < 1)
	        	return;
	        
	        GsonBuilder builder = new GsonBuilder();
	        builder.registerTypeAdapter(PlaceDeserializer.class, new PlaceDeserializer());
	        builder.registerTypeAdapter(TransportDeserializer.class, new TransportDeserializer());
	        builder.registerTypeAdapter(RouteDeserializer.class, new RouteDeserializer());
	        
	    	Gson gson = new Gson();
	    	Route route = gson.fromJson(result, Route.class);
	    	
	    	HashMap<String, HashMap<String, String>> nuevos_destinos = new HashMap<String, HashMap<String, String>>();
	    	
	    	int currentorder = 0;
	    	for(Place json_destino : route.Places)
	    	{
	    		if(json_destino == null || json_destino.Name == null || json_destino.Name.length() < 1)
	    			continue;
	    		
	    		HashMap<String, String> destino = new HashMap<String, String>();
	    		destino.put("orden", Integer.toString(currentorder));
	    		destino.put("direccion", json_destino.Address);
	    		destino.put("notas", json_destino.Description);
	    		
	    		destino.put("estado", "No Visitado");
	    		
	    		nuevos_destinos.put(json_destino.Name, destino);
	    		
	    		currentorder += 1;
	    	}
	    	
	    	actualizarDestinos(nuevos_destinos);
	    }
	}
	
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode, int hideWaitMsg)
	{
	    Map<String, String> map = new HashMap<String, String>();
	    map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
	    map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
	    map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
	    map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
	    map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
	 
	    GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this, hideWaitMsg);
	    asyncTask.execute(map);
	}
	
	public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints)
	{
		Log.w("Log", "Ejecute la vuelta");
		
	    PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);
	    for(int i = 0 ; i < directionPoints.size() ; i++)
	    {
	    	LatLng pos = directionPoints.get(i);
	        rectLine.add(pos);
	        Log.w("Log", "Ejecute la vuelta");
	        Log.w("lat", Double.toString(pos.latitude));
	        Log.w("long", Double.toString(pos.longitude));
	    }
	    
	    if(rutaActual != null)
	    	rutaActual.remove();
	    
	    rutaActual = map.addPolyline(rectLine);
	}

	public void cambiarEstado(String key, String estado)
	{
		Destinos.get(key).remove("estado");
		Destinos.get(key).put("estado", estado);
		
		actualizarCheckboxes();
		actualizarMarcadores();
		
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("id", Integer.toString(my_ID));
		data.put("name", indice_destino_actual);
		String newstate = "";
		if(estado == "No Visitado")
			newstate = "notvisited";
		else if(estado == "Visitado")
			newstate = "visited";
		else if(estado == "Ausente")
			newstate = "ausente";
		data.put("estado", newstate);
		AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
		asyncHttpPost.execute(APPENGINE_ADDR_STATE);
		
		// Actualizar destino:
		String dest = elegirProximoDestino();
		if(dest == null)
			sinDestino();
		else
		{
			cambiarDestino(dest);
		}
			
	}
	
	public void sinDestino()
	{
		if(rutaActual != null)
			rutaActual.remove();
	}
	
	public String elegirProximoDestino()
	{
		String res = null;
		
		for(String dest : Destinos.keySet())
		{
			if(Destinos.get(dest).get("estado") == "No Visitado")
			{
				res = dest;
				break;
			}
		}
		
		return res;
	}
	
	public void actualizarCheckboxes()
	{
		if(Destinos.get(indice_destino_actual).get("estado") == "Visitado")
			chkVisitado.setChecked(true);
		else
			chkVisitado.setChecked(false);
		
		if(Destinos.get(indice_destino_actual).get("estado") == "Ausente")
			chkAusente.setChecked(true);
		else
			chkAusente.setChecked(false);
	}
	
	public void actualizarMarcadores()
	{
		Log.w("actualizarMarcadores", "Estado");
		
		for(String key : Markers.keySet())
		{
			String estado = Destinos.get(key).get("estado");
			List<Marker> marcadores = Markers.get(key);
			
			if(indice_destino_actual == key)
				Log.w("Destino actual", "Estado = " + estado);
			
			if(estado == "Visitado")
			{
				marcadores.get(0).setVisible(true);
				marcadores.get(1).setVisible(false);
				marcadores.get(2).setVisible(false);
			}
			else if(estado == "No Visitado")
			{
				marcadores.get(0).setVisible(false);
				marcadores.get(1).setVisible(true);
				marcadores.get(2).setVisible(false);
				
			}
			else if(estado == "Ausente")
			{
				marcadores.get(0).setVisible(false);
				marcadores.get(1).setVisible(false);
				marcadores.get(2).setVisible(true);
			}
		}
	}
	
	public void onClick(DialogInterface dialog, int which) {
		Log.w("Seleccion", Integer.toString(which));
		if(which == -1)
		{
			// No Visitado
			cambiarEstado(last_opened_destination, "No Visitado");
		}
		else if(which == -2)
		{
			// Ausente
			cambiarEstado(last_opened_destination, "Ausente");
		}
		else if(which == -3)
		{
			// Visitado
			cambiarEstado(last_opened_destination, "Visitado");
		}
		
		skip_next_info = true;
		spinner.setSelection(Integer.parseInt(Destinos.get(indice_destino_actual).get("orden")), true);
		
		actualizarCheckboxes();
	}
	
	public void setPublicPosition()
	{
		if(my_location != null)
		{
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("id", Integer.toString(my_ID));
			data.put("lat", Double.toString(my_location.latitude));
			data.put("lng", Double.toString(my_location.longitude));
			data.put("alert", Boolean.toString(alert));
			AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
			asyncHttpPost.execute(APPENGINE_ADDR_POSITION);
			
			Log.w("POST", "Envie posicion");
		}
	}
	
	public void setAlert(boolean value)
	{
		alert = value;
	}
}