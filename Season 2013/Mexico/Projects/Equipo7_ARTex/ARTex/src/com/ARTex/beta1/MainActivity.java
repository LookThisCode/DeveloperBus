package com.ARTex.beta1;

import android.os.Bundle;
import android.view.Menu;

import com.qualcomm.QCARUnityPlayer.QCARPlayerActivity;

public class MainActivity extends QCARPlayerActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
