package ngvl.android.imfine.ui;

import ngvl.android.imfine.R;
import ngvl.android.imfine.persistence.Preferences;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;

public class LoginActivity extends Activity 
	implements ConnectionCallbacks, OnConnectionFailedListener, OnClickListener {
	
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private SignInButton mBtnSignIn;
	private ProgressBar mProgress;
	
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private boolean mLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mLogout = getIntent().getBooleanExtra("logout", false);
		
		mPlusClient = new PlusClient.Builder(
				this, // Context 
				this, // ConnectionCallbacks
				this) // OnConnectionFailedListener
				.build();
		
		mProgress = (ProgressBar)findViewById(R.id.progressBar);
		mBtnSignIn = (SignInButton)findViewById(R.id.sign_in_button);
		mBtnSignIn.setOnClickListener(this);
		
		connectGPlus();
	}

	@Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
            if (mConnectionResult == null) {
            	mPlusClient.connect();
            	mProgress.setVisibility(View.VISIBLE);
            	
            } else {
                try {
                    mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
                } catch (SendIntentException e) {
                    // Try connecting again.
                	e.printStackTrace();
                    mConnectionResult = null;
                    connectGPlus();
                }
            }
        }
    }
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mProgress.getVisibility() == View.VISIBLE) {
			// The user clicked the sign-in button already. Start to resolve
			// connection errors. Wait until onConnected() to dismiss the
			// connection dialog.
			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					e.printStackTrace();
					connectGPlus();
				}
			}
		}
		// Save the result and resolve the connection failure upon a user click.
		mConnectionResult = result;
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			connectGPlus();
		} else {
			mProgress.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mProgress.setVisibility(View.INVISIBLE);

		if (mLogout){
			mLogout = false;
			mPlusClient.clearDefaultAccount();
			mPlusClient.disconnect();
			Preferences.saveGPlusUserId(this, "");
			
		} else {
			String accountName = mPlusClient.getAccountName();
			Toast.makeText(this, 
					accountName + " is connected.", 
					Toast.LENGTH_LONG).show();
			
			Preferences.saveGPlusUserId(this, mPlusClient.getCurrentPerson().getId());
			
			Intent it = new Intent(this, MainActivity.class);
			startActivity(it);
			finish();
		}
	}

	@Override
	public void onDisconnected() {
		mProgress.setVisibility(View.INVISIBLE);
	}
	
	private void connectGPlus(){
		mPlusClient.connect();
    	mProgress.setVisibility(View.VISIBLE);
	}
}