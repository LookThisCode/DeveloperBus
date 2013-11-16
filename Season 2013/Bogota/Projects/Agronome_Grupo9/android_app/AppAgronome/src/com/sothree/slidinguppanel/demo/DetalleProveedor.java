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
	
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	private GoogleMap map;
	Button comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_proveedor);
		comments = (Button) findViewById(R.id.button1);
		
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
		      Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
		          .title("Hamburg"));
		      Marker kiel = map.addMarker(new MarkerOptions()
		          .position(KIEL)
		          .title("Kiel")
		          .snippet("Kiel is cool")
		          .icon(BitmapDescriptorFactory
		              .fromResource(R.drawable.ic_launcher)));
		    }
	}

}
