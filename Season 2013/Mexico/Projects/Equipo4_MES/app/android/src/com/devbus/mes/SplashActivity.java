package com.devbus.mes;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	private long SPLASH_TIME = 5000; //5 segundos
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Pantalla completa
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash_screen);
		
		//Cargador dummy
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, Login.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task, SPLASH_TIME);//Pasado los 5 segundos dispara la tarea   					
	}	
}