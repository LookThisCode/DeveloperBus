package com.devbus.sweethistory;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Main class to handle Support Library Navigation Drawer in all application views
 */
public class BaseDrawerActivity extends FragmentActivity {

	protected DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;

	private static final int SWEET_HISTORY = 0;
	private static final int BABY_REGISTER = 1;
	private static final int ALARM_REGISTER = 2;
	private static final int DOCTOR_REGISTER = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_activity);

		String[] menuSections = getResources().getStringArray(R.array.sectionNames);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuSections));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
			mDrawerLayout,         /* DrawerLayout object */
			R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
			R.string.drawer_open,  /* "open drawer" description for accessibility */
			R.string.drawer_close  /* "close drawer" description for accessibility */) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(getTitle());
				ActivityCompat.invalidateOptionsMenu(BaseDrawerActivity.this); // creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(R.string.app_name);
				ActivityCompat.invalidateOptionsMenu(BaseDrawerActivity.this); // creates call to
				// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// Set custom layout to action bar
		// Set up the action bar.
		final ActionBar ab = getActionBar();
		if (null != ab) {
			ab.setDisplayHomeAsUpEnabled(true);

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				ab.setHomeButtonEnabled(true);
			}
		}
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	/**
	 * Swaps fragments in the main content view
	 */
	private void selectItem(int position) {
		// Create a new fragment and specify the planet to show based on position
		Intent intent = null;
		switch (position) {
			case BABY_REGISTER:
				intent = new Intent(this, FormBabyActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				break;
			case ALARM_REGISTER:
				intent = new Intent(this, AlarmActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				break;
			case DOCTOR_REGISTER:
				intent = new Intent(this, FormDoctorActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				break;
			case SWEET_HISTORY:
				intent = new Intent(this, MainActivity.class);
				break;
		}
		if (intent != null){
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDrawerLayout.closeDrawer(GravityCompat.START);
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Check if the navigation its open
	 *
	 * @return TRUE if the navigation drawer its open, FALSE otherwise
	 */
	public Boolean isDrawerOpen() {
		return mDrawerLayout.isDrawerOpen(GravityCompat.START);
	}

	/**
	 * Close the current drawer
	 */
	protected void closeDrawer() {
		mDrawerLayout.closeDrawer(GravityCompat.START);
	}
}
