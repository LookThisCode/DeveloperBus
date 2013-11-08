package mx.developerbus.foodbus;

import mx.developerbus.foodbus.adpt.ListMenuOptionsAdapter;
import mx.developerbus.foodbus.enm.MenuOptionType;
import mx.developerbus.foodbus.manager.FoodBusManager;
import mx.developerbus.foodbus.model.MenuOption;
import mx.developerbus.foodbus.utl.foodBUtil;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FoodBus_Main extends FoodBusManager {
	
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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_food_bus);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#23a7d9")));
		loginGplus();
	}

	
	private void loginGplus(){
		try {
			initDraw();
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
		Bitmap perfil = null;
		Bitmap circleBitmap = Bitmap.createBitmap(perfil.getWidth(),perfil.getHeight(), Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(perfil, TileMode.CLAMP, TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setShader(shader);
		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(perfil.getWidth()/2, perfil.getHeight()/2, perfil.getWidth()/2, paint);
		userProfilePic.setImageBitmap(circleBitmap);
		userProfileTxt.setText("Hamburguesas Truck");
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
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.food_bus__main, menu);
		return true;
	}

}
