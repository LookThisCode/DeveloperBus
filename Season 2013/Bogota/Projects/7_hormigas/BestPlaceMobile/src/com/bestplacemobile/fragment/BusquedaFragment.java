package com.bestplacemobile.fragment;

import java.util.ArrayList;
import com.bestplacemobile.R;
import com.bestplacemobile.activities.DistribuidorDetalleActivity;
import com.bestplacemobile.adapters.CustomAdapterDistribuidores;
import com.bestplacemobile.models.ItemDistribuidor;
import android.content.Intent;
import android.os.Bundle;
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
		String[] palabrasClaves = new String[] { "Zapatos", "Camisas", "Bolsos" };

		item = new ItemDistribuidor(1, "Ventas y Servicios al Detal",
				palabrasClaves, "5", 5, 3);

		items.add(item);

		item = new ItemDistribuidor(2, "Grandes Superficies de Occidente",
				palabrasClaves, "6", 6, 6);

		items.add(item);

		item = new ItemDistribuidor(3, "Pinilla y Asociados", palabrasClaves,
				"7", 7, 8);

		items.add(item);

		CustomAdapterDistribuidores adapter = new CustomAdapterDistribuidores(
				getActivity(), items, true);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ItemDistribuidor clickedItemDistribuidor = (ItemDistribuidor) l
				.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(),
				DistribuidorDetalleActivity.class);
		intent.putExtra("nombre", clickedItemDistribuidor.getNombreCompania());
		intent.putExtra("coment",
				clickedItemDistribuidor.getNumeroComentarios());
		intent.putExtra("calificacion",
				clickedItemDistribuidor.getCalificacion());
		startActivity(intent);
	}

	public void updateListView(ArrayList<ItemDistribuidor> items) {
		CustomAdapterDistribuidores adapter = new CustomAdapterDistribuidores(
				getActivity(), items, true);
		setListAdapter(adapter);
	}

}
