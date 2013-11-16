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
	
	private ImageView imagen, suscribir;
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
	}
	
	public void setProveedor(Proveedor p) {
		final int id_proveedor = p.getId();
		nombre.setText(p.getNombre());
		ubicacion.setText(p.getCiudad());
		calificacion.setNumStars(5);
		calificacion.setRating(Float.parseFloat(p.getRating()));
		calificacion.setEnabled(false);
		//descargarFoto("http://apps.imaginamos.com.co/taxisya/"+servicio.getFoto());
		suscribir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getContext(), Comentarios.class);
				i.putExtra("id_proveedor", "1");
				getContext().startActivity(i);
			}
		});
	
	}

}
