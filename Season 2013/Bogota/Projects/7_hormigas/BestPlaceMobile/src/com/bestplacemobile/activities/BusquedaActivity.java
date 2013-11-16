package com.bestplacemobile.activities;

import com.bestplacemobile.R;
import com.bestplacemobile.fragment.BusquedaFragment;
import com.bestplacemobile.fragment.BusquedaGeneralFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class BusquedaActivity extends FragmentActivity {

	BusquedaGeneralFragment busquedaGeneralFragment;
	BusquedaFragment busquedaFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda);

		busquedaFragment = new BusquedaFragment();
		busquedaGeneralFragment = new BusquedaGeneralFragment();

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.busqueda_fragment, busquedaGeneralFragment);
		transaction.add(R.id.resultados_busqueda_fragment, busquedaFragment);

		transaction.commit();
				
	}
}
