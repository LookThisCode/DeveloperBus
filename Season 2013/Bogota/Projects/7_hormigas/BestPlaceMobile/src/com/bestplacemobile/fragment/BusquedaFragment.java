package com.bestplacemobile.fragment;

import java.util.ArrayList;
import com.bestplacemobile.R;
import com.bestplacemobile.adapters.CustomAdapterDistribuidores;
import com.bestplacemobile.models.ItemConfiguracion;
import com.bestplacemobile.models.ItemDistribuidor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BusquedaFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater infalter, ViewGroup group,
			Bundle svaedInstanceState) {
		return infalter.inflate(R.layout.list_busqueda_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayList<ItemDistribuidor> items = new ArrayList<ItemDistribuidor>();

		ItemDistribuidor item = null;
		String[] palabrasClaves = new String[] { "zapatos", "mesas", "Botellas" };

		item = new ItemDistribuidor("Distribuidores prueba", palabrasClaves,
				"5", 5, 7);

		items.add(item);

		item = new ItemDistribuidor("Distribuidores prueba 2", palabrasClaves,
				"6", 6, 6);

		items.add(item);

		item = new ItemDistribuidor("Distribuidores prueba 3", palabrasClaves,
				"7", 7, 8);

		items.add(item);

		CustomAdapterDistribuidores adapter = new CustomAdapterDistribuidores(
				getActivity(), items, true);

		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ItemConfiguracion clickedItem = (ItemConfiguracion) l
				.getItemAtPosition(position);
		// Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
		// startActivity(intent);
	}

}
