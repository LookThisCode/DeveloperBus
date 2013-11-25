package ngvl.android.imfine.ui;

import ngvl.android.imfine.R;
import ngvl.android.imfine.model.Message;
import ngvl.android.imfine.persistence.Preferences;
import ngvl.android.imfine.ui.ListMessageFragment.OnMessageSelectedListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements OnMessageSelectedListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_logout){
			Preferences.storeRegistrationId(getApplicationContext(), "");
			
			Intent it = new Intent(this, LoginActivity.class);
			it.putExtra("logout", true);
			startActivity(it);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onMessageSelected(Message message) {
		if (isTablet()){
			ViewMessageFragment d = ViewMessageFragment.newInstance(message);
			
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.root_detalhe, d)
				.commit();	
			
		} else {
			Intent it = new Intent(this, ViewMessageActivity.class);
			it.putExtra("message", message);
			startActivity(it);		
		}
	}

	private boolean isTablet() {
		return findViewById(R.id.root_detalhe) != null;
	}
}
