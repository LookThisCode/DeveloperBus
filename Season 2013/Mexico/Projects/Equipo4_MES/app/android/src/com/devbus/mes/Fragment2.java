package com.devbus.mes;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.devbus.mes.adapters.AdapterRuta;
import com.devbus.mes.datadummy.ItemRuta;

public class Fragment2 extends Fragment {
	private ArrayList<ItemRuta> items = new ArrayList<ItemRuta>();
	private ListView lista;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		items.add( new ItemRuta(1,"200m", "Caminar hacia la estacion Juanacatlan"));
		items.add( new ItemRuta(2,"Coyoacan", "Toma el metro con dirección observatorio"));
		items.add( new ItemRuta(2,"Coyoacan", "Toma el metro con dirección observatorio"));
		items.add( new ItemRuta(1,"400m", "Camina 5 cuadras a tu derecha"));
		
		View view = inflater.inflate(R.layout.fragment2, container, false);
		
		Context context = getActivity().getApplicationContext();
		lista = (ListView) view.findViewById(R.id.lista_rutas);
		lista.setAdapter(new AdapterRuta(context, R.layout.item_ruta, items));
		
		
		
		
		
		return view;
	}
	

}
