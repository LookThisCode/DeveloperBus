package com.sothree.slidinguppanel.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.appspot.agrono_me.agronome.Agronome;
import com.appspot.agrono_me.agronome.Agronome.Proveedor.Consultarproveedortag;
import com.appspot.agrono_me.agronome.model.ProveedorDTO;
import com.appspot.agrono_me.agronome.model.ProveedorDTOCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.sothree.slidinguppanel.demo.messageEndpoint.MessageEndpoint;
import com.sothree.slidinguppanel.demo.messageEndpoint.model.CollectionResponseMessageData;
import com.sothree.slidinguppanel.demo.messageEndpoint.model.MessageData;



import android.media.audiofx.BassBoost.Settings;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainProveedores extends Activity {

	static ArrayList<Proveedor> proveedores;
	public ListView lista;
	static ProveedorAdapter adaptador;
	private ProgressDialog pDialog;
	public Agronome service;
	public ImageButton buscar;
	public EditText textoBusqueda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_proveedores);

		lista = (ListView) findViewById(R.id.lista_proveedores);
		buscar = (ImageButton) findViewById(R.id.lupa);
		textoBusqueda = (EditText) findViewById(R.id.txt_busqueda);
		
		
		
		proveedores = new ArrayList<Proveedor>();

		adaptador = new ProveedorAdapter(proveedores);
		lista.setAdapter(adaptador);
		lista.setScrollingCacheEnabled(false);

		Agronome.Builder builder = new Agronome.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		builder.setApplicationName("AppAgronome");
		service = builder.build();


		buscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new ProveedoresTask().execute(textoBusqueda.getText().toString());
				
			}
		});

		//new ProveedoresTask().execute("keyword");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_proveedores, menu);
		return true;
	}

	public static void cargarDatos(ProveedorDTOCollection resultado){
		try {
			proveedores.clear();
			Log.i("Proveedores: ", resultado.toString());
			List<ProveedorDTO> providers = resultado.getItems();
			
			for (int i = 0; i < providers.size(); i++) {
				
				ProveedorDTO p = providers.get(i);
				Proveedor pro = new Proveedor(0, p.getNombre(), p.getNit(), p.getTelefono(), p.getLatitud(), p.getLongitud(), p.getRating(), p.getCiudad());
				proveedores.add(pro);
			}
			adaptador.notifyDataSetChanged();

			/*for (int i = 0; i < 10; i++) {
				Proveedor p = new Proveedor( 1 , "Didier Neira", "900.800.700", "3103445300", "3000", "4000", "4.5", "Bogota");
				proveedores.add(p);
			}*/
		} 
		catch (Exception e) {

			e.printStackTrace();
		}
		//adaptador.notifyDataSetChanged();

	}

	private class ProveedoresTask  extends AsyncTask<String, Void, ProveedorDTOCollection > {
		Exception exceptionThrown = null;



		@Override
		protected ProveedorDTOCollection  doInBackground(String... params) {
			String search_tag = params[0];
			Log.i("Buscando:" , search_tag);
			try {
				ProveedorDTOCollection execute = service.proveedor().consultarproveedortag(search_tag).execute();
				return execute;
			} catch (IOException e) {
				exceptionThrown = e;
				e.printStackTrace();
				return null;
				//Handle exception in PostExecute
			}            
		}
		
		protected void onPreExecute() {
            pDialog = new ProgressDialog(MainProveedores.this);
            pDialog.setMessage("Un momento estamos buscando procesando tu información....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		@Override
		protected void onPostExecute(ProveedorDTOCollection result) {
			pDialog.dismiss();
			// Check if exception was thrown
			if (exceptionThrown != null) {
				Log.e(RegisterActivity.class.getName(), 
						"Exception when listing Messages", exceptionThrown);
			}
			else {
				//Log.i("Respuesta", items.toString());
			}
			
			cargarDatos(result);
			
			

		}

		   
	}

}
