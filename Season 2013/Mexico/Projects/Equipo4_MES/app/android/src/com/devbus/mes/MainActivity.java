package com.devbus.mes;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;


public class MainActivity extends FragmentActivity {
	
	private static final String[] titles = new String[] { 
		"Mapa",
		"Mis rutas", 
		"Preferencias", 
	};
	
	private static final Class<?>[] fragments = new Class[] {
		Fragment1.class,
		Fragment2.class,
		Fragment3.class
	}; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.s_barra));
		
		//Set the pager with an adapter
		ViewPager pager = (ViewPager)findViewById(R.id.pager);
		pager.setAdapter(new TestAdapter(getSupportFragmentManager()));
		
		// Bind the title indicator to the adapter
		PageIndicator indicator = (TitlePageIndicator)findViewById(R.id.titles);
		indicator.setViewPager(pager);	

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		crearMenu(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return menuSeleccion(item);
	}
	
	private void crearMenu(Menu menu) {
		MenuItem item1 = menu.add(0, 0, 0, "Item 1");
		{
			item1.setIcon(android.R.drawable.ic_dialog_dialer);
			item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
		MenuItem item2 = menu.add(0, 0, 0, "Item 2");
		{
			item2.setIcon(android.R.drawable.ic_menu_search);
			item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}	
	}
	
	private boolean menuSeleccion(MenuItem item) {
		switch (item.getItemId()) {
		case 0: 
			Toast.makeText(this, "Sin funcionalidad", Toast.LENGTH_LONG).show();
			return true;
			
		case 1:
			Toast.makeText(this, "Sin funcionalidad", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;	
	}
	
	
	class TestAdapter extends FragmentPagerAdapter {     
		private final int count = titles.length;

		public TestAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			try {
				fragment = (Fragment)fragments[position].newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fragment;    
		}

		@Override
		public int getCount() {
			return count;
		}

		@SuppressLint("DefaultLocale")
		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position].toUpperCase();
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if( keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 )
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			 
			dialog.setMessage("¿Esta seguro que desea salir?");
			dialog.setCancelable(false);
			dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			 
			  @Override
			  public void onClick(DialogInterface dialog, int which) {
				  finish();
			  }
			});
			
			dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			 
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			      dialog.cancel();
			   }
			});
			dialog.show();
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}