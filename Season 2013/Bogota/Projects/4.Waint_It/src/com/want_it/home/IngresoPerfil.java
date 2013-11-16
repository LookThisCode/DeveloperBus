package com.want_it.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.want_it.clases.*;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient.OnAccessRevokedListener;

public class IngresoPerfil extends Activity {

	private Button btnDesea;
	private Button btnOferta;
	private BPOServer bpos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingreso_perfil);
		btnDesea=(Button) findViewById(R.id.btnDesea);
		btnOferta=(Button) findViewById(R.id.btnOferta);
		listener();
	}
	
	

	private void listener() {
		btnDesea.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent(getApplicationContext(),PerfilUsuario.class);
				startActivity(i);
				
			}
		});
		
		btnOferta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), bpos.buscarOfertar("Materiales", "Madera"), Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(),PerfilEmpresa.class);
				startActivity(i);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ingreso_perfil, menu);
		return true;
	}
	
	public boolean onMenuItemSelected(int featureId, MenuItem item){
	    switch (item.getItemId()){
	        //Cerrar Sesión
	        case R.id.action_sign_out:
	            if (Main.plusClient.isConnected()){
	                Main.plusClient.clearDefaultAccount();
	                Main.plusClient.disconnect();
	                Main.plusClient.connect();
	 
	                Toast.makeText(IngresoPerfil.this,
	                    "Sesión Cerrada.",
	                    Toast.LENGTH_LONG).show();
	            }
	 
	            return true;
	        //Revocar permisos a la aplicación
	        case R.id.action_revoke_access:
	            if (Main.plusClient.isConnected())
	            {
	                Main.plusClient.clearDefaultAccount();
	 
	                Main.plusClient.revokeAccessAndDisconnect(
	                    new OnAccessRevokedListener() {
	                        @Override
	                        public void onAccessRevoked(ConnectionResult status)
	                        {
	                            Toast.makeText(IngresoPerfil.this,
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

}
