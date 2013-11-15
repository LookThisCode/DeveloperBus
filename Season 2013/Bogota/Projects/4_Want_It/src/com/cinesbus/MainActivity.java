package com.cinesbus;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.*;
import com.google.android.gms.common.GooglePlayServicesClient.*;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.PlusClient;

import com.cinesbus.util.Util;
//import com.cinesbus.sql.Conexion;
//import com.cinesbus.sql.objetos.Conf;

public class MainActivity extends Activity implements View.OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener {
	private static final String TAG = "ExampleActivity";
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	private Context context;

	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;

	private String link;
	private Util u;
	private String IMEI;

	private GoogleCloudMessaging gcm;
	private String regid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		u = new Util(this);

		mPlusClient = new PlusClient.Builder(this, this, this).setActions(
				"http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity").build();

		findViewById(R.id.sign_in_button).setOnClickListener(this);

		// Progress bar to be displayed if the connection failure is not
		// resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Iniciando...");
	}

	@Override
	protected void onStart() {
		super.onStart();
		mPlusClient.connect();
	}

	@Override
	public void onDisconnected() {
		Log.d(TAG, "disconnected");
	}

	@Override
	protected void onStop() {
		super.onStop();
		mPlusClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mConnectionProgressDialog.isShowing()) {
			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					mPlusClient.connect();
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
			mPlusClient.connect();
		}
	}

	String name = "";

	@Override
	public void onConnected(Bundle connectionHint) {
		// String accountName = mPlusClient.getAccountName();
		// Toast.makeText(this, accountName + " is connected.",
		// Toast.LENGTH_LONG).show();

		mConnectionProgressDialog.dismiss();
		
		

		//Conexion c = new Conexion(this);
		//Conf co = new Conf();
		//co.id = "IMEI";
		//co.desc = IMEI;

		//List<Conf> list = c.consultaConf(co);
		//if (list.size() > 0) {
			//c.modificaConf(co);
		//} else {
		//	c.insertaConf(co);
		//}
		//c.close();
		//name = mPlusClient.getCurrentPerson().getDisplayName();
		
		//**String fecha_nac = mPlusClient.getCurrentPerson().getBirthday();
		//if (fecha_nac == null || fecha_nac.equals("null")
			//	|| fecha_nac.equals("")) {
			//fecha_nac = "1900-01-01";
		//}

		link = "";

		link += "&name="
				+ mPlusClient.getCurrentPerson()
						.getDisplayName();
		link += "&nick="
				+ mPlusClient.getCurrentPerson().getNickname();
		link += "&mail=" + mPlusClient.getAccountName();
		link += "&version="
				+ getText(R.string.versionName).toString();
	
		link += "&genero="
				+ mPlusClient.getCurrentPerson().getGender()
						+ "";
		link += "&lenguaje="
				+ mPlusClient.getCurrentPerson().getLanguage();
		link += "&url="
				+ mPlusClient.getCurrentPerson().getUrl();
		

		//context = getApplicationContext();
		
		Toast.makeText(this, link + " YA ESTAS CONECTADO", Toast.LENGTH_LONG)
		.show();
		
		Intent i = new Intent(MainActivity.this, AdapterPerfil.class);
		startActivity(i);
		
		}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
			if (mConnectionResult == null) {
				mConnectionProgressDialog.show();
			} else {
				try {
					mConnectionResult.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					// Try connecting again.
					mConnectionResult = null;
					mPlusClient.connect();
				}
			}
		}
	}
	
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(MainActivity.this, "Dispositivo no soportado.",
						Toast.LENGTH_LONG).show();
			}
			return false;
		}
		return true;
	}
	
	

}