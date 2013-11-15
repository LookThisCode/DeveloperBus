package com.devbus.sweethistory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class FormBabyActivity extends BaseDrawerActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		FrameLayout mainFrame = (FrameLayout) findViewById(R.id.main_frame);
		View v = getLayoutInflater().inflate(R.layout.form_baby, null);
		mainFrame.addView(v);
	}

}
