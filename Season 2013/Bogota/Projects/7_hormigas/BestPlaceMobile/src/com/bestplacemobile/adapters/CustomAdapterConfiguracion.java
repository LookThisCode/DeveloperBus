package com.bestplacemobile.adapters;

import java.util.ArrayList;

import com.bestplacemobile.R;
import com.bestplacemobile.models.ItemConfiguracion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterConfiguracion extends ArrayAdapter<ItemConfiguracion> {

	private ArrayList<ItemConfiguracion> data;
	private LayoutInflater inflater;

	public CustomAdapterConfiguracion(Context context,
			ArrayList<ItemConfiguracion> objects, boolean isList) {
		super(context, -1, objects);
		this.data = objects;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		ItemConfiguracion actual = data.get(position);
		int tipoItem = actual.getTipo();

		int imgResource = 0;

		if (tipoItem == 1) {
			imgResource = R.drawable.ic_config; // config
		}
		if (tipoItem == 2) {
			imgResource = R.drawable.ic_edit; // edit
		}
		if (tipoItem == 3) {
			imgResource = R.drawable.ic_request; // request
		}
		if (tipoItem == 4) {
			imgResource = R.drawable.ic_active; // active
		}

		int layout = R.layout.list_row_conf;

		if (convertView == null) {

			convertView = inflater.inflate(layout, null);

			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.img_row_config);
			holder.title = (TextView) convertView
					.findViewById(R.id.txt_row_desc_config);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.title.setText(actual.getInfo());
		holder.img.setImageResource(imgResource);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView img;
		public TextView title;
	}

}
