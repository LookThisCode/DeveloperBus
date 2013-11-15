package mx.developerbus.foodbus;
/**
 * Copyright [2013] [Diego Ernesto Franco Chanona, Irving Lopez Perez, Miriam Alejandra Lugo Muciño, Raymundo Juarez Cortes]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

import mx.developerbus.foodbus.adpt.ListMenuOptionsAdapter;
import mx.developerbus.foodbus.enm.MenuOptionType;
import mx.developerbus.foodbus.frgm.Fragment_MiBus;
import mx.developerbus.foodbus.manager.FoodBusManager;
import mx.developerbus.foodbus.model.MenuOption;
import mx.developerbus.foodbus.utl.foodBUtil;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class FoodBus_Main extends FoodBusManager implements LocationListener{
	
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerLeft;
	
	private ListView mDrawerList;
	
	private ListMenuOptionsAdapter listOptionsAdapter;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	private LinearLayout userProfileInfo;
	private ImageView userProfilePic;
	private TextView userProfileTxt;
	
	LocationManager locationManager;
	GoogleMap map;
	Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_food_bus);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#23a7d9")));

		try {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//			map  = ((SupportMapFragment) getSupportFragmentManager()
//			        .findFragmentById(R.id.mapFragment)).getMap();
			setFragment(getString(R.string.app_name), Fragment_MiBus.class);
			initDraw();
			map.setMyLocationEnabled(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	private void initDraw() throws Exception {
		mTitle = mDrawerTitle = getTitle();
		if (mDrawerLayout == null) {
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			setOptions();
		}
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer,0,0) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);

			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}
	
	private void setOptions() throws Exception{
		View left = findViewById(R.id.menuOptions);
		if (left != null && left instanceof LinearLayout) {
			mDrawerLeft = (LinearLayout) left;

			mDrawerList = (ListView) mDrawerLeft
					.findViewById(R.id.menuOptionsList);
			userProfileInfo = (LinearLayout) mDrawerLeft
					.findViewById(R.id.userProfileInfo);
			userProfilePic = (ImageView) mDrawerLeft
					.findViewById(R.id.usrProfilePic);
			userProfileTxt = (TextView) mDrawerLeft
					.findViewById(R.id.userProfileTxt);
			setProfileGplus();
			initListItems();
			mDrawerList
			.setOnItemClickListener(new DrawerItemClickListener());
	mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
			GravityCompat.START);
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	getSupportActionBar().setHomeButtonEnabled(true);
		}
	}
	
	private void setProfileGplus(){
//		Bitmap perfil = null;
//		Bitmap circleBitmap = Bitmap.createBitmap(perfil.getWidth(),perfil.getHeight(), Bitmap.Config.ARGB_8888);
//		BitmapShader shader = new BitmapShader(perfil, TileMode.CLAMP, TileMode.CLAMP);
//		Paint paint = new Paint();
//		paint.setShader(shader);
//		Canvas c = new Canvas(circleBitmap);
//		c.drawCircle(perfil.getWidth()/2, perfil.getHeight()/2, perfil.getWidth()/2, paint);
//		userProfilePic.setImageBitmap(circleBitmap);
//		userProfileTxt.setText("Hamburguesas Truck");
	}
	
	private void initListItems(){
		listOptionsAdapter = new ListMenuOptionsAdapter(this);
		for (MenuOptionType type : MenuOptionType.values()) {
			MenuOption opt = new MenuOption();
			opt.setType(type);
			opt.setTitle(foodBUtil.getResourceString(this, type.getTextResource()));
			opt.setResourceId(R.layout.foodbus_home_options_item);
			listOptionsAdapter.add(opt);
		}
		mDrawerList.setAdapter(listOptionsAdapter);
	}
	
	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	selectItem(position);
}
}

	private void selectItem(int position) {
		MenuOption option = listOptionsAdapter.getItem(position);
		try {
			switch (option.getType()) {
			case MiBus:
			case MisPedidos:
			case MisPublicaciones:
			case MiMenu:
				setFragment(option);
				break;
			default:
				break;
			}
			closeLeftDrawer();
		} catch (Exception e) {
			Log.e("ERROR", "ERROR SpmMain - selectItem :\n" + e.getMessage());
		}
		mDrawerList.setItemChecked(position, true);
	}
	
	public void setFragment(MenuOption option) throws Exception {
		setFragment(option.getTitle(), option.getType().getFragment());
	}
	
	public void setFragment(String title, Class<? extends Fragment> frg)
			throws Exception {
		try {
			setFragment(title, frg, false);
		} catch (Exception e) {
			throw new Exception("ERROR Home - setHomeFragmen : "
					+ e.getMessage());
		}
	}
	
	public void setFragment(String title, Class<? extends Fragment> frg,
			boolean stacked) throws Exception {
		try {
			if (mDrawerLayout != null) {
				mDrawerLayout
						.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			}
			foodBUtil.setFragmentWorkspace(false, getSupportFragmentManager(),
					frg, stacked);
			setTitle(title);
		} catch (Exception e) {
			throw new Exception("ERROR Home - setHomeFragmen : "
					+ e.getMessage());
		}
	}
	
	private void closeLeftDrawer() {
		mDrawerLayout.closeDrawer(mDrawerLeft);
	}
	
	@Override
	public void onBackPressed() {
	}


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_localizacion:
			updateLocalization();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.food_bus__main, menu);
		return true;
	}


	private void updateLocalization(){
		LatLng lt = getLocation();
		double lat = lt.latitude;
		double lon = lt.longitude;
		Toast.makeText(getApplicationContext(), "Haz actualizado tu ubicación: " + lat + lon, Toast.LENGTH_SHORT).show();
	}

	public LatLng getLocation()
    {
     // Get the location manager
     LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
     Criteria criteria = new Criteria();
     String bestProvider = locationManager.getBestProvider(criteria, false);
     Location location = locationManager.getLastKnownLocation(bestProvider);
     Double lat,lon;
     try {
       lat = location.getLatitude ();
       lon = location.getLongitude ();
       return new LatLng(lat, lon);
     }
     catch (NullPointerException e){
         e.printStackTrace();
       return null;
     }
    }


	@Override
	public void onLocationChanged(Location location) {
		Toast.makeText(getApplicationContext(), "Estas aquí: " + location.getLatitude() + location.getLongitude(), Toast.LENGTH_SHORT).show();
	}



	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	
}
