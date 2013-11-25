package br.com.expenseme.ui;

import android.os.Bundle;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Base class for all Expenseme Activities that need fragments.
 */
public class ExpensemeFragmentActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        Views.inject(this);
    }

}
