package com.example.jellybean_logistic;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.location.LocationListener;

public class AlertButton implements OnClickListener {

	private MainActivity activity;
	private boolean value;
	
	public AlertButton(MainActivity external_activity)
	{
		activity = external_activity;
		value = false;
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(value == false)
			value = true;
		else
			value = false;
		
		activity.setAlert(value);
	}
	
}