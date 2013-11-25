package com.devbusbrasil.powerup;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

public class MainActivity extends Activity {
	
	private static MainActivity mInstance;
	public static MainActivity GetInstance() {
		return mInstance;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}