package com.bestplacemobile.fragment;

import java.util.ArrayList;

import com.bestplacemobile.R;
import com.bestplacemobile.adapters.CustomAdapterConfiguracion;
import com.bestplacemobile.models.ItemConfiguracion;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ConfiguracionFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater infalter, ViewGroup group,
			Bundle svaedInstanceState) {
		return infalter.inflate(R.layout.list_conf_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayList<ItemConfiguracion> itemsConf = new ArrayList<ItemConfiguracion>();

		ItemConfiguracion itemConf;
		itemConf = new ItemConfiguracion("Configure la forma en que recibira notificaciones", "0", 1);
		itemsConf.add(itemConf);

		itemConf = new ItemConfiguracion("Organize sus productos, fotografias y la información que desea mostrar", "0", 2);
		itemsConf.add(itemConf);

		itemConf = new ItemConfiguracion("Tiene notificaciones pendientes por revisar", "0", 3);
		itemsConf.add(itemConf);

		itemConf = new ItemConfiguracion("Verifique los compromisos comerciales", "0", 4);
		itemsConf.add(itemConf);

		CustomAdapterConfiguracion adapter = new CustomAdapterConfiguracion(
				getActivity(), itemsConf, true);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {	

	}

}
