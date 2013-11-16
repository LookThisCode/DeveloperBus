package com.devbus.mes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragment3 extends Fragment {
	private ListView listaPref;
	String[] values = new String[] {
			"Mi Perfil",
			"Mis rutas",
			"Mis mapas",
			"Redes sociales",
			"Privacidad",
			"Acerca de"
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment3, container, false);
		
		listaPref = (ListView) view.findViewById(R.id.listaPref);
		
		Context context = getActivity().getApplicationContext();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		// Assign adapter to ListView
        listaPref.setAdapter(adapter); 
        
		return view;
	}

}
