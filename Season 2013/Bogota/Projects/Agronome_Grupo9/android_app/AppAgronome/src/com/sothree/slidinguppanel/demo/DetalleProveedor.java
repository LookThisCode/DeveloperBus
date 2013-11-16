package com.sothree.slidinguppanel.demo;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DetalleProveedor extends Activity {
	
	private LatLng ME;
	private LatLng PROVIDER;
	private GoogleMap map;
	Button comments;
	private String lat;
	private String lng;
	private String lat2;
	private String lng2;
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_proveedor);
		comments = (Button) findViewById(R.id.button1);
		
		Bundle reicieveParams = getIntent().getExtras();
        lat = reicieveParams.getString("lat");
        lng = reicieveParams.getString("lng");
        
        gps = new GPSTracker(getApplicationContext());
        
        miPosicion();
        
        PROVIDER = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        
        ME = new LatLng(Double.parseDouble(lat2), Double.parseDouble(lng2));
		
		comments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), Comentarios.class);
				i.putExtra("id_proveedor", "1");
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getBaseContext().startActivity(i);
			}
		});
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		    
		    if (map!=null){
		      Marker hamburg = map.addMarker(new MarkerOptions().position(ME)
		          .title("Yo"));
		      Marker kiel = map.addMarker(new MarkerOptions()
		          .position(PROVIDER)
		          .title("Proveedor")
		          .snippet("Acá estoy para ofrecerte lo mejor.")
		          .icon(BitmapDescriptorFactory
		              .fromResource(R.drawable.ic_launcher)));
		    }
	}
	
	public void miPosicion(){
		if(gps.canGetLocation()){
        	
        	lat2 = String.valueOf(gps.getLatitude());
        	lng2 = String.valueOf(gps.getLongitude());
        	
        	// \n is for new line
        	//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitud + "\nLong: " + longitud, Toast.LENGTH_LONG).show();	
        }else{
        	// can't get location
        	// GPS or Network is not enabled
        	// Ask user to enable GPS/network in settings
        	gps.showSettingsAlert();
        }
	}

}
