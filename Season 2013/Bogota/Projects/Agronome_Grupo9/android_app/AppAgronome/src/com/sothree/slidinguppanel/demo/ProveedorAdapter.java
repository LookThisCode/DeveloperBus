package com.sothree.slidinguppanel.demo;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProveedorAdapter extends BaseAdapter {
	
	private ArrayList<Proveedor> proveedores;
	
	public ProveedorAdapter(ArrayList<Proveedor> proveedores) {
		this.proveedores = proveedores;
		
		//Cada vez que cambiamos los elementos debemos noficarlo
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return proveedores.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return proveedores.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ProveedorView view;
		if (convertView == null) //NO existe, creamos uno
			view = new ProveedorView(parent.getContext());
		else					//Existe, reutilizamos
			view = (ProveedorView) convertView;
		
		/**
		 * Ahora tenemos que darle los valores correctos, para ello usamos
		 * el método setRectangulo pasándole el rectángulo a mostrar
		 * y finalmente devolvemos el view.
		 */
		
		view.setProveedor(proveedores.get(position));
		
		return view;
	}
	
}
