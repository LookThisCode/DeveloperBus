package com.bestplacemobile.activities;

import com.bestplacemobile.R;
import com.bestplacemobile.fragment.DetalleDistribuidorFragment;
import com.bestplacemobile.fragment.GridImagenesDistribuidor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class DistribuidorDetalleActivity extends FragmentActivity {

	private GridImagenesDistribuidor gridImagenesDistribuidor;
	private DetalleDistribuidorFragment detalleDistribuidorFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidor_detalle);

		gridImagenesDistribuidor = new GridImagenesDistribuidor();
		detalleDistribuidorFragment = new DetalleDistribuidorFragment();

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.frag_datos_dist, detalleDistribuidorFragment);
		transaction.add(R.id.imgs_distribuidor, gridImagenesDistribuidor);

		transaction.commit();

	}

}
