package com.sothree.slidinguppanel.demo;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

public class Comentarios extends Activity {
	
	static ArrayList<Comentario> comentarios;
	public ListView lista;
	static ComentarioAdapter adaptador;
	private ProgressDialog pDialog;;
	public Button guardar;
	EditText comentario;
	RatingBar puntaje;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comentarios);
		lista = (ListView) findViewById(R.id.listaComentarios);
		guardar = (Button) findViewById(R.id.btn_comentar);
		comentario = (EditText) findViewById(R.id.comentario);
		puntaje = (RatingBar) findViewById(R.id.puntuacion);
		
		comentarios = new ArrayList<Comentario>();
		
		adaptador = new ComentarioAdapter(comentarios);
		lista.setAdapter(adaptador);
		lista.setScrollingCacheEnabled(false);
		guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Comentario c = new Comentario("Didier Neira", comentario.getText().toString(), puntaje.getNumStars());
				comentarios.add(c);
				adaptador.notifyDataSetChanged();
			}
		});
		cargarDatos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comentarios, menu);
		
		
		return true;
	}
	
	public static void cargarDatos(){
    	try {
	    	//proveedores.clear();
	    	
	    	for (int i = 0; i < 10; i++) {
	    		Comentario c = new Comentario("Ramon Valdez", "Excelente servicio y seriedad en el negocio. 100% Recomendado.", 100);
				comentarios.add(c);
	    	}
    	} 
    	catch (Exception e) {
			
			e.printStackTrace();
		}
    	adaptador.notifyDataSetChanged();
    	
    }
	

}
