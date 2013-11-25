package com.developerbus.getcondo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.developerbus.getcondo.models.Issue;
import com.developerbus.getcondo.utils.GsonRequest;
import com.developerbus.getcondo.utils.Settings;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getString(R.string.title_news);
        mRequestQueue = Volley.newRequestQueue(this);
        loadSettings();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentToLoad = null;

        switch (position) {

            // News
            case 0:
                fragmentToLoad = new NewsFragment(mRequestQueue);
                mTitle = getString(R.string.title_news);
                break;
            // Problems
            case 1:
                fragmentToLoad = (Fragment) new IssuesFragment(mRequestQueue);
                mTitle = getString(R.string.title_problems);
                break;

            // Events
            case 2:
                fragmentToLoad = new EventsFragment(mRequestQueue);
                mTitle = getString(R.string.title_events);
                break;
            default:
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentToLoad)
                .commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_news);
                break;
            case 2:
                mTitle = getString(R.string.title_problems);
                break;
            case 3:
                mTitle = getString(R.string.title_events);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    public void loadSettings() {
        Settings.initInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_create_issue:
                displayCreateIssueFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayCreateIssueFragment() {
        Fragment createIssueFragment = (Fragment) new CreateIssueFragment(mRequestQueue);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mTitle = getString(R.string.title_create_issue);
        getActionBar().setTitle(mTitle);
        fragmentManager.beginTransaction()
                .replace(R.id.container, createIssueFragment)
                .commit();
    }

}
