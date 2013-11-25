package br.com.expenseme.ui;

import android.support.v4.widget.DrawerLayout;
import br.com.expenseme.R;

import com.actionbarsherlock.app.ActionBarDrawerToggle;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ActionBarSidebarToggle extends ActionBarDrawerToggle {
    public ActionBarSidebarToggle(final SherlockFragmentActivity activity, final DrawerLayout drawerLayout) {
        super(activity, activity.getSupportActionBar(), drawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
    }
}
