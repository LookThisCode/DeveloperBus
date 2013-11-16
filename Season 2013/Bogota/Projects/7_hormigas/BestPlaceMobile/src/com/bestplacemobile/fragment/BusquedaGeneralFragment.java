package com.bestplacemobile.fragment;

import java.util.ArrayList;

import com.bestplacemobile.R;
import com.bestplacemobile.models.ItemDistribuidor;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class BusquedaGeneralFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater infalter, ViewGroup group,
			Bundle svaedInstanceState) {
		return infalter.inflate(R.layout.frag_busq_gral, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Button btnLogin = (Button) getActivity().findViewById(R.id.btnBusqueda);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new TaskBusquedaDistribuidores().execute();
			}
		});
	}

	class TaskBusquedaDistribuidores extends
			AsyncTask<Object, Void, ArrayList<ItemDistribuidor>> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPostExecute(ArrayList<ItemDistribuidor> result) {
			super.onPostExecute(result);
			progressDialog.dismiss();

			if (!result.isEmpty()) {

				// updateListView(result);
			} else {
				Toast.makeText(getActivity(), "Se Encontraron Distribuidores",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Buscando");
			progressDialog.show();
		}

		@Override
		protected ArrayList<ItemDistribuidor> doInBackground(Object... params) {
			ArrayList<ItemDistribuidor> items = new ArrayList<ItemDistribuidor>();

			try {

				// items = LDistribuidores.ObtenerDistribuidores();
							
				//ListFragment frg = (ListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.resultados_busqueda_fragment);

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
								
			} catch (Exception e) {
				e.printStackTrace();
			}

			return items;
		}

	}

}
