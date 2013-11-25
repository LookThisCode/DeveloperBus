package br.com.expenseme.ui;

import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import br.com.expenseme.ExpensemeServiceProvider;
import br.com.expenseme.R;
import br.com.expenseme.authenticator.LogoutService;
import br.com.expenseme.core.ExpensemeService;
import br.com.expenseme.util.SafeAsyncTask;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends ExpensemeFragmentActivity implements DrawerListener {

    @InjectView(R.id.content)
    FrameLayout contentLayout;

    ExpensemeServiceProvider serviceProvider;

    private DrawerLayout drawerLayout;
    private ActionBarSidebarToggle drawerToggle;
    private ActionBar actionBar;
    private TextView btnTrackExpense;
    private TextView btnVenues;
    private View btnLogout;
    private TextView btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        serviceProvider = new ExpensemeServiceProvider();

        setContentView(R.layout.carousel_view);

        drawerLayout = (DrawerLayout)findViewById(R.id.content);
        btnTrackExpense = (TextView)findViewById(R.id.track_expense);
        btnVenues = (TextView)findViewById(R.id.venues);
        btnLogout = findViewById(R.id.logout);
        btnHome = (TextView)findViewById(R.id.home);

        drawerLayout.setDrawerListener(this);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerToggle = new ActionBarSidebarToggle(this, drawerLayout);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Home");

        setNavListeners();

        Views.inject(this);

        checkAuth();
    }

    @Override
    protected void onPostCreate(final Bundle sSavedInstanceState) {
        super.onPostCreate(sSavedInstanceState);
        drawerToggle.syncState();
    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final ExpensemeService svc = serviceProvider.getService(CarouselActivity.this);
                return svc != null;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button,
                    // etc).
                    // Since auth could not take place, lets finish this
                    // activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                pushNewFragment(new HomeFragment(), false);
            }
        }.execute();
    }

    private void setNavListeners() {
        btnHome.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewFragment(new HomeFragment(), false);
                        actionBar.setTitle(btnHome.getText());
                        drawerLayout.closeDrawers();
                    }
                });

        btnTrackExpense.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewFragment(new TrackExpenseFragment(), false);
                        actionBar.setTitle(btnTrackExpense.getText());
                        drawerLayout.closeDrawers();
                    }
                });

        btnVenues.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushNewFragment(new VenueMapFragment(), false);
                        actionBar.setTitle(btnVenues.getText());
                        drawerLayout.closeDrawers();
                    }
                });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutService(CarouselActivity.this).logout(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        });
    }

    private void pushNewFragment(final Fragment newFragment, final boolean addToBackStack) {

        if (newFragment != null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction transaction = fragmentManager
                    .beginTransaction();

            final String fragmentTag = newFragment.getClass().getSimpleName();

            transaction.replace(R.id.contentPanel, newFragment, fragmentTag);

            if (addToBackStack)
                transaction.addToBackStack(fragmentTag);

            transaction.commit();

            fragmentManager.executePendingTransactions();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggleDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toggleDrawer() {
        if (isDrawerOpen())
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            drawerLayout.openDrawer(GravityCompat.START);
    }

    private boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onDrawerClosed(View arg0) {
        drawerToggle.onDrawerClosed(drawerLayout);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onDrawerOpened(View arg0) {
        drawerToggle.onDrawerOpened(drawerLayout);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.action_bar_title, null));
    }

    @Override
    public void onDrawerSlide(View arg0, float arg1) {
        drawerToggle.onDrawerSlide(drawerLayout, arg1);
    }

    @Override
    public void onDrawerStateChanged(int arg0) {
        drawerToggle.onDrawerStateChanged(arg0);
    }
}
