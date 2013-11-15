package mx.developerbus.foodbus;

import mx.developerbus.foodbus.model.UserGPlus;
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
import android.widget.Toast;

/**
 * 
 * @author Diego
 *Copyright [2013] [Diego Ernesto Franco Chanona, Irving Lopez Perez, Miriam Alejandra Lugo Muciño, Raymundo Juarez Cortes]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

public class Login_Bus extends Activity{

	public static UserGPlus user; 
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
			user = new UserGPlus(Login_Bus.this);
			user.start(new Intent(Login_Bus.this, FoodBus_Main.class));
		}
	};

	OnClickListener noCuenta = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(Login_Bus.this, "Registrate", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onBackPressed() {
	}
	
	
}
