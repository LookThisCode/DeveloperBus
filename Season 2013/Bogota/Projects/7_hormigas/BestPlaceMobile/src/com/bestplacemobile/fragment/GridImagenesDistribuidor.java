package com.bestplacemobile.fragment;

import java.util.ArrayList;
import com.bestplacemobile.R;
import com.bestplacemobile.adapters.CustomAdapImagenesDetalDist;
import com.bestplacemobile.models.ItemImagenDetalladaDist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class GridImagenesDistribuidor extends Fragment {

	private GridView grid;	

	@Override
	public View onCreateView(LayoutInflater infalter, ViewGroup group,
			Bundle svaedInstanceState) {
		View view = infalter.inflate(com.bestplacemobile.R.layout.grid_imagenes_distribuidor, null);
		grid = (GridView) view.findViewById(R.id.grid_img_dist);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayList<ItemImagenDetalladaDist> imgs = new ArrayList<ItemImagenDetalladaDist>();		
		
		ItemImagenDetalladaDist item = new ItemImagenDetalladaDist("test", "Test", "12", "12");
		imgs.add(item);
		
		item = new ItemImagenDetalladaDist("test", "Test", "12", "12");
		imgs.add(item);
		
		item = new ItemImagenDetalladaDist("test", "Test", "12", "12");
		imgs.add(item);
		
		item = new ItemImagenDetalladaDist("test", "Test", "12", "12");
		imgs.add(item);		
		
		item = new ItemImagenDetalladaDist("test", "Test", "12", "12");
		imgs.add(item);
		
		item = new ItemImagenDetalladaDist("test", "Test", "12", "12");
		imgs.add(item);
		
		CustomAdapImagenesDetalDist adapter = new CustomAdapImagenesDetalDist(getActivity(), imgs, false);
		grid.setAdapter(adapter);		
	}

}
