package com.bestplacemobile.adapters;

import java.util.ArrayList;

import com.bestplacemobile.R;
import com.bestplacemobile.models.ItemImagenDetalladaDist;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CustomAdapImagenesDetalDist extends
		ArrayAdapter<ItemImagenDetalladaDist> {

	private ArrayList<ItemImagenDetalladaDist> data;
	private LayoutInflater inflater;

	public CustomAdapImagenesDetalDist(Context context,
			ArrayList<ItemImagenDetalladaDist> objects, boolean isList) {
		super(context, -1, objects);
		this.data = objects;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
				
		int imgResource = 0;

		int layout = R.layout.grid_element;

		if (convertView == null) {

			convertView = inflater.inflate(layout, null);

			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.img_row);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.img.setImageResource(imgResource);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView img;
	}

}
