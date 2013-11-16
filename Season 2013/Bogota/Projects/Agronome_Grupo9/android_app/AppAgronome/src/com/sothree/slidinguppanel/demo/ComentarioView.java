package com.sothree.slidinguppanel.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class ComentarioView extends LinearLayout{
	
	private ImageView imagen, suscribir;
	private Bitmap foto;
	private TextView usuario,comentario;
	private RatingBar rate;

	public ComentarioView(Context context) {
		super(context);
		inflate(context, R.layout.fila_comentario, this);
		imagen = (ImageView) findViewById(R.id.fotoProveedor); 
		usuario = (TextView) findViewById(R.id.usuario);
		comentario = (TextView) findViewById(R.id.comentario);	
		rate = (RatingBar) findViewById(R.id.ratingBar1);
	}
	
	public void setComentario(Comentario c) {
		usuario.setText("NOMBRE: "+c.getUsuario());
		comentario.setText("COMENTARIO: "+c.getComentario());
		rate.setRating(c.getRating());
		rate.setEnabled(false);
		
		//descargarFoto("http://apps.imaginamos.com.co/taxisya/"+servicio.getFoto());

	}

}
