package ngvl.android.imfine.ui;

import ngvl.android.imfine.R;
import ngvl.android.imfine.model.Message;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class ViewMessageActivity extends ActionBarActivity {
	
	boolean mFromPush;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_message);
		
		mFromPush = getIntent().getBooleanExtra("push", false);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
		
		Message message = (Message)getIntent().getSerializableExtra("message");
		
		ViewMessageFragment d = ViewMessageFragment.newInstance(message);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, d)
			.commit();	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void finish() {
		super.finish();
		if (mFromPush){
			Intent it = new Intent(this, MainActivity.class);
			it.addFlags(
					Intent.FLAG_ACTIVITY_CLEAR_TOP | 
					Intent.FLAG_ACTIVITY_SINGLE_TOP |
					Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(it);
		}
	}
}
