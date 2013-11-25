package br.com.expenseme.ui;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import android.content.Intent;
import android.os.Bundle;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * Base activity for a Expenseme activity which does not use fragments.
 */
public abstract class ExpensemeActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        // Used to inject views with the Butterknife library
        Views.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  // This is the home button in the top left corner of the screen.
                // Dont call finish! Because activity could have been started by an outside activity and the home button would not operated as expected!
                Intent homeIntent = new Intent(this, CarouselActivity.class);
                homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
