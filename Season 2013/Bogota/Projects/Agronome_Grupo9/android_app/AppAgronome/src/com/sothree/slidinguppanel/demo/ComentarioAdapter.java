package com.sothree.slidinguppanel.demo;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ComentarioAdapter extends BaseAdapter {
	
	private ArrayList<Comentario> comentarios;
	
	public ComentarioAdapter(ArrayList<Comentario> comentarios) {
		this.comentarios = comentarios;
		
		//Cada vez que cambiamos los elementos debemos noficarlo
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comentarios.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comentarios.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ComentarioView view;
		if (convertView == null) //NO existe, creamos uno
			view = new ComentarioView(parent.getContext());
		else					//Existe, reutilizamos
			view = (ComentarioView) convertView;
		
		/**
		 * Ahora tenemos que darle los valores correctos, para ello usamos
		 * el método setRectangulo pasándole el rectángulo a mostrar
		 * y finalmente devolvemos el view.
		 */
		
		view.setComentario(comentarios.get(position));
		
		return view;
	}
	
	

}
