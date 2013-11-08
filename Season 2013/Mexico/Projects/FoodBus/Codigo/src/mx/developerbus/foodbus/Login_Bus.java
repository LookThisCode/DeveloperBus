package mx.developerbus.foodbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Login_Bus extends Activity{

	private ImageView imgButton;
	private ImageButton btnLogin;
	private TextView txtCuenta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		
		imgButton = (ImageView)findViewById(R.id.imgBackgroud);
		btnLogin = (ImageButton)findViewById(R.id.btnLogin);
		txtCuenta = (TextView)findViewById(R.id.txt_nocuenta);
		
		btnLogin.setOnClickListener(login);
		txtCuenta.setOnClickListener(noCuenta);
	}
	
	OnClickListener login = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(Login_Bus.this, FoodBus_Main.class);
			startActivity(i);
		}
	};

	OnClickListener noCuenta = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
	
}
