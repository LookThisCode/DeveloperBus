package com.devbus.mes.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbus.mes.R;
import com.devbus.mes.datadummy.ItemRuta;

public class AdapterRuta extends ArrayAdapter { 
	private ArrayList items;
	private Context context;
	
	public AdapterRuta(Context context, int textViewResourceId,ArrayList items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.items=items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if( convertView == null ) 
		{
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.item_ruta, null);
		
			holder = new ViewHolder();
			holder.imagen = (ImageView) convertView.findViewById(R.id.img_item);
			holder.ruta = (TextView) convertView.findViewById(R.id.ruta_item);    
		    holder.desc = (TextView) convertView.findViewById(R.id.desc_item);
		
		    convertView.setTag(holder);
		}
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		ItemRuta rutas = (ItemRuta) items.get(position);
		
		/**
		 * 1 = Eco
		 * 2 = Metro
		 * 3 = Metrobus
		 */
		
		switch( rutas.getTipo() ) {
		case 1:holder.imagen.setImageResource(R.drawable.eco_48x48); break;
		case 2: holder.imagen.setImageResource(R.drawable.metro_48x48); break;
		case 3: holder.imagen.setImageResource(R.drawable.metrobus_48x48); break;
		}
		
		holder.ruta.setText(rutas.getRuta());
		holder.desc.setText(rutas.getDesc());
		
        return convertView;
	}
	
	class ViewHolder {
		ImageView imagen;
		TextView ruta;
		TextView desc;
	}
}