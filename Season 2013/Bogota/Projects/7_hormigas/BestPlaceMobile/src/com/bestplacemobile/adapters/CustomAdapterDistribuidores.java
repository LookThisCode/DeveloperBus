package com.bestplacemobile.adapters;

import java.util.ArrayList;
import com.bestplacemobile.R;
import com.bestplacemobile.models.ItemDistribuidor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterDistribuidores extends ArrayAdapter<ItemDistribuidor> {

	private ArrayList<ItemDistribuidor> data;
	private LayoutInflater inflater;

	public CustomAdapterDistribuidores(Context context,
			ArrayList<ItemDistribuidor> objects, boolean isList) {
		super(context, -1, objects);
		this.data = objects;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		ItemDistribuidor actual = data.get(position);

		int layout = R.layout.list_row_busqueda;

		if (convertView == null) {

			convertView = inflater.inflate(layout, null);

			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.img_row_busqueda);
			holder.nombreCompania = (TextView) convertView
					.findViewById(R.id.txt_row_busq_nombreCompania);
			holder.palabrasClaves = (TextView) convertView
					.findViewById(R.id.txt_row_busq_palabrasClaves);
			// holder.calificacion = (TextView) convertView
			// .findViewById(R.id.txt_row_busq_calificacion);
			holder.numComentarios = (TextView) convertView
					.findViewById(R.id.txt_row_busq_num_comentarios);
			holder.numComentGooglePlus = (TextView) convertView
					.findViewById(R.id.txt_row_numComentgoogleplus);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.nombreCompania.setText(actual.getNombreCompania());

		StringBuilder palabrasClaves = new StringBuilder();

		for (String palClave : actual.getPalabarasClaves()) {
			palabrasClaves.append(palClave);
			palabrasClaves.append(",");
		}

		String palClavesConcat = palabrasClaves.substring(0,
				palabrasClaves.length() - 1);

		holder.palabrasClaves.setText(palClavesConcat);
		// holder.calificacion.setText(actual.getCalificacion());
		holder.nombreCompania.setText(actual.getNombreCompania());
		holder.numComentarios.setText(String.valueOf(actual
				.getNumeroComentarios()));
		holder.numComentGooglePlus.setText(String.valueOf(actual
				.getNumeroComentariosGoogle()));

		//BitmapManager.getInstance().loadBitmap(actual.getUrlLogo(), holder.img);
		holder.img.setImageResource(0);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView img;
		public TextView nombreCompania;
		public TextView palabrasClaves;
		public TextView calificacion;
		public TextView numComentarios;
		public TextView numComentGooglePlus;

	}

}
