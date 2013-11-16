package com.want_it.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnAccessRevokedListener;

public class Main extends Activity  implements ConnectionCallbacks, OnConnectionFailedListener{
 
 	private SignInButton btnSignIn;
	public static PlusClient plusClient;
	private ProgressDialog connectionProgressDialog;
	private ConnectionResult connectionResult;
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    public static String usuario;
    public static String avatar;
    public static String id;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSignIn = (SignInButton)findViewById(R.id.sign_in_button);
		 
	    plusClient = new PlusClient.Builder(this, this, this).setVisibleActivities("http://schemas.google.com/AddActivity","http://schemas.google.com/ListenActivity").build();
	 
	    connectionProgressDialog = new ProgressDialog(this);
	    connectionProgressDialog.setMessage("Conectando...");
	    
	    btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!plusClient.isConnected()){
		            if (connectionResult == null){
		                connectionProgressDialog.show();
		            }
		            else{
		                try{
		                    connectionResult.startResolutionForResult( Main.this,REQUEST_CODE_RESOLVE_ERR);
		                }catch (SendIntentException e){
		                    connectionResult = null;
		                    plusClient.connect();
		                }
		            }
		        }
				
			}});
	}
	protected void onActivityResult(int requestCode, int responseCode, Intent intent)
	{
	    if (requestCode == REQUEST_CODE_RESOLVE_ERR &&responseCode == RESULT_OK)
	    {
	        connectionResult = null;
	        plusClient.connect();
	    }
	}
	
	@Override
	protected void onStart()
	{
	    super.onStart();
	    plusClient.connect();
	}
	 
	@Override
	protected void onStop()
	{
	    super.onStop();
	    plusClient.disconnect();
	}
	
	   @Override
	    public void onConnected(Bundle connectionHint){
	        connectionProgressDialog.dismiss();
	        usuario=plusClient.getCurrentPerson().getDisplayName();
	        id=plusClient.getCurrentPerson().getId();
	        avatar=plusClient.getCurrentPerson().getImage().getUrl();
	        Intent i = new Intent(Main.this, IngresoPerfil.class);
			startActivity(i);
			finish();
	        Toast.makeText(this, "Conectado!",
	            Toast.LENGTH_LONG).show();
	    }
	 
	    @Override
	    public void onDisconnected(){
	        Toast.makeText(this, "Desconectado!",
	            Toast.LENGTH_LONG).show();
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onMenuItemSelected(int featureId, MenuItem item){
	    switch (item.getItemId()){
	        //Cerrar Sesión
	        case R.id.action_sign_out:
	            if (plusClient.isConnected()){
	                plusClient.clearDefaultAccount();
	                plusClient.disconnect();
	                plusClient.connect();
	 
	                Toast.makeText(Main.this,
	                    "Sesión Cerrada.",
	                    Toast.LENGTH_LONG).show();
	            }
	 
	            return true;
	        //Revocar permisos a la aplicación
	        case R.id.action_revoke_access:
	            if (plusClient.isConnected())
	            {
	                plusClient.clearDefaultAccount();
	 
	                plusClient.revokeAccessAndDisconnect(
	                    new OnAccessRevokedListener() {
	                        @Override
	                        public void onAccessRevoked(ConnectionResult status)
	                        {
	                            Toast.makeText(Main.this,
	                                "Acceso App Revocado",
	                                Toast.LENGTH_LONG).show();
	                        }
	                    });
	            }
	 
	            return true;
	        default:
	            return super.onMenuItemSelected(featureId, item);
	    }
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (connectionProgressDialog.isShowing())
	    {
	        if (result.hasResolution())
	        {
	            try
	            {
	                result.startResolutionForResult(this,
	                    REQUEST_CODE_RESOLVE_ERR);
	            }
	            catch (SendIntentException e)
	            {
	                plusClient.connect();
	            }
	        }
	    }
	 
	    connectionResult = result;
		
	}

}
