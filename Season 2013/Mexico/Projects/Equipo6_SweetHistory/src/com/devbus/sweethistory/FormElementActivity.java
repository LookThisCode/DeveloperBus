package com.devbus.sweethistory;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

public class FormElementActivity extends Activity {

	private ShareActionProvider mShareActionProvider;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String typeElement = this.getIntent().getStringExtra("TYPE_ELEMENT");
        Integer elementId= this.getIntent().getIntExtra("ELEMENT_ID",0);

		setContentView(R.layout.form_element);
		ImageView imgFormElement = (ImageView) this
				.findViewById(R.id.imgFormElement);
		TextView txtFormElement = (TextView) this
				.findViewById(R.id.txtFormElement);
		LinearLayout linearLayout = (LinearLayout) this
				.findViewById(R.id.imgBackground);

		if (typeElement.compareTo("event") == 0) {// Event
			imgFormElement.setImageResource(R.drawable.evento);
			linearLayout.setBackgroundResource(R.drawable.circle_evento);
			txtFormElement.setText("EVENTO");
		} else {// Sintom
			imgFormElement.setImageResource(R.drawable.sintoma);
			txtFormElement.setText("SINTOMA");
			linearLayout.setBackgroundResource(R.drawable.circle_sintoma);
		}
		
		ImageView camera = (ImageView)this.findViewById(R.id.camera);
		camera.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startCamera();
				
			}
		});	
	}
	
	private void startCamera(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = getOutputMediaFileUri(); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);		
	}
	
	private Uri getOutputMediaFileUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            //Toast.makeText(this, "Imagen guardada en tus im�genes." + data.getData(), Toast.LENGTH_LONG).show();
	        	Toast.makeText(this, "Imagen guardada en tus im�genes.", Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.getMenuInflater().inflate(R.menu.menu_form_element, menu);

		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);

		// Fetch and store ShareActionProvider
		mShareActionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(item);		
		
		// Return true to display menu
		return true;
	}
	
	private void setShareIntent(Intent shareIntent) {
	    if (mShareActionProvider != null) {
	        mShareActionProvider.setShareIntent(shareIntent);
	    }
	}

	private Intent createShareIntent() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "S�ntoma del d�a.");
		sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "doctor@gmail.com" });
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
		return sendIntent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.saveElement:
			this.saveElement();
			break;

		case R.id.menu_item_share:
			//Create el share Intent	
			setShareIntent(createShareIntent());
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void saveElement() {

	}

}