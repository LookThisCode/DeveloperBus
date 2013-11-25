package com.devbusbrasil.powerup;

import com.devbusbrasil.powerup.lib.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class Login extends Activity implements View.OnClickListener,
PlusClient.ConnectionCallbacks, PlusClient.OnConnectionFailedListener, PlusClient.OnAccessRevokedListener {
	
    private static final int DIALOG_GET_GOOGLE_PLAY_SERVICES = 1;

    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES = 2;

    private PlusClient mPlusClient;
    private SignInButton mSignInButton;
    private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPlusClient = new PlusClient.Builder(this, this, this)
                .setActions("http://schemas.google.com/AddActivity")
                .setScopes(Scopes.PLUS_LOGIN, Scopes.PLUS_PROFILE)
                .build();
        User.GetInstance().setPlusClient(mPlusClient);
        
		SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
		if (!prefs.getBoolean("SIGNED_IN", false)) {
			  mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
		      mSignInButton.setOnClickListener(this);
		}
    }


    @Override
    public void onStart() {
        super.onStart();
        mPlusClient.connect();
    }

    @Override
    public void onStop() {
        mPlusClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        String currentPersonName = mPlusClient.getCurrentPerson() != null
                                   ? mPlusClient.getCurrentPerson().getDisplayName()
                                   : "desconhecido";

        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("SIGNED_IN", true);
        edit.commit();
        
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDisconnected() {
        mPlusClient.connect();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id != DIALOG_GET_GOOGLE_PLAY_SERVICES) {
            return super.onCreateDialog(id);
        }

        int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            return null;
        }
        if (GooglePlayServicesUtil.isUserRecoverableError(available)) {
            return GooglePlayServicesUtil.getErrorDialog(
                    available, this, REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES);
        }
        return new AlertDialog.Builder(this)
                .setMessage("Erro genérico")
                .setCancelable(true)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SIGN_IN
            || requestCode == REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES) {
            if (resultCode == RESULT_OK && !mPlusClient.isConnected()
                && !mPlusClient.isConnecting()) {
                // This time, connect should succeed.
                mPlusClient.connect();
            }
        }
    }

    @Override
    public void onAccessRevoked(ConnectionResult status) {
        if (status.isSuccess()) {
        } else {
            mPlusClient.disconnect();
        }
        mPlusClient.connect();
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.sign_in_button:
                int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                if (available != ConnectionResult.SUCCESS) {
                    showDialog(DIALOG_GET_GOOGLE_PLAY_SERVICES);
                    return;
                }

                try {
                    mConnectionResult.startResolutionForResult(this, REQUEST_CODE_SIGN_IN);
                } catch (Exception e) {
                    // Fetch a new result to start.
                    mPlusClient.connect();
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mConnectionResult = connectionResult;
    }
}