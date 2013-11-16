package com.bestplacemobile.activities;

import com.bestplacemobile.R;
import com.bestplacemobile.fragment.ConfiguracionFragment;
import com.bestplacemobile.fragment.RegistroFragment;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@TargetApi(14)
public class MainActivity extends FragmentActivity implements
		ListView.OnItemClickListener {

	private ListView drawerList;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToogle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		drawerList = (ListView) findViewById(R.id.left_drawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		ArrayAdapter<String> drawerAdapter = new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, getResources().getStringArray(
						R.array.array_MenuPpal));

		drawerList.setAdapter(drawerAdapter);
		drawerList.setOnItemClickListener(this);

		drawerToogle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}

		};

		drawerLayout.setDrawerListener(drawerToogle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		selectItem(0);
	}

	public void selectItem(int position) {
		Fragment f = null;

		if (position == 0) {
			f = new ConfiguracionFragment();
		}
		if (position == 1) {
			f = new RegistroFragment();
		}
		if (position == 2) {
			Intent intBusqueda = new Intent(MainActivity.this,
					BusquedaActivity.class);
			startActivity(intBusqueda);
			return;
		}

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.main_content, f).commit();
		drawerList.setItemChecked(position, true);
		setTitle(drawerList.getItemAtPosition(position).toString());
		drawerLayout.closeDrawer(drawerList);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToogle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToogle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToogle.syncState();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		selectItem(arg2);
	}

}
