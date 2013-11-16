package com.sothree.slidinguppanel.demo;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProveedorView extends LinearLayout {
	
	private ImageView imagen, suscribir, vermas;
	private Bitmap foto;
	private TextView nombre,ubicacion;
	private RatingBar calificacion;

	public ProveedorView(Context context) {
		super(context);
		inflate(context, R.layout.fila_proveedor, this);
		imagen = (ImageView) findViewById(R.id.fotoProveedor); 
		nombre = (TextView) findViewById(R.id.nombre);
		ubicacion = (TextView) findViewById(R.id.ubicacion); 
		calificacion = (RatingBar) findViewById(R.id.prov_rate);	
		suscribir = (ImageView) findViewById(R.id.btn_suscribir);
		vermas = (ImageView) findViewById(R.id.vermas);
	}
	
	public void setProveedor(Proveedor p) {
		final int id_proveedor = p.getId();
		final String lat = p.getLat();
		final String lng = p.getLng();
		nombre.setText("NOMBRE: "+ p.getNombre());
		ubicacion.setText("CIUDAD: "+p.getCiudad());
		calificacion.setNumStars(5);
		calificacion.setRating(Float.parseFloat(p.getRating()));
		calificacion.setEnabled(false);
		//descargarFoto("http://apps.imaginamos.com.co/taxisya/"+servicio.getFoto());
		suscribir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getContext(), Comentarios.class);
				i.putExtra("id_proveedor", id_proveedor);
				i.putExtra("lng", lng);
				i.putExtra("lat", lat);
				getContext().startActivity(i);
			}
		});
		
		vermas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getContext(), DetalleProveedor.class);
				i.putExtra("id_proveedor", id_proveedor);
				i.putExtra("lng", lng);
				i.putExtra("lat", lat);
				getContext().startActivity(i);
			}
		});
	
	}

}
