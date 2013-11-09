package com.example.artex3;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		TabHost tabHost = getTabHost();		
		
		Intent l = new Intent().setClass(this, Lista.class);
	    TabHost.TabSpec spec4 = tabHost.newTabSpec("Más Info").setIndicator("Más Información").setContent(l);
	    tabHost.addTab(spec4);
		
        Intent i = new Intent().setClass(this, Unity.class);		
		 //Intent i = new Intent().setClass(this, Unity.class);
	     TabHost.TabSpec spec = tabHost.newTabSpec("Explora").setIndicator("Explora").setContent(i); // <- references the intent we just created
	     tabHost.addTab(spec);       

	     
	     //Intent j = new Intent().setClass(this, DashboardActivity.class);
	     Intent j = new Intent().setClass(this, Punteros.class);
	     TabHost.TabSpec spec2 = tabHost.newTabSpec("Encuentra librerías").setIndicator("Encuentra librerías").setContent(j); // <- references the intent we just created
	     tabHost.addTab(spec2);        	     
	     
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}