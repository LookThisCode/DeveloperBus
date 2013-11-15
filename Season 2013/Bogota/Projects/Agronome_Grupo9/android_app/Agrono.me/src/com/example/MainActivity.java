package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
  /** Called when the activity is first created. */
	
	private ImageView image;
	private Animation animFadeIn;
	private Animation animFadeOut;
	private boolean tutorial;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
    
      
      image = (ImageView)findViewById(R.id.my_image);
    
      animFadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
      animFadeOut = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
 
      image.startAnimation(animFadeOut);
      SystemClock.sleep(2000);
      image.startAnimation(animFadeIn);
      
      SharedPreferences prefs = getSharedPreferences("agronome",Context.MODE_PRIVATE);
      
      tutorial = prefs.getBoolean("tutorial",false);
      
      Intent in;
      	if (tutorial) {
      		in = new Intent(MainActivity.this, ScreenSlideActivity.class);
		} else {
			in = new Intent(MainActivity.this, ScreenSlideActivity.class);
		}
		startActivity(in);
  }
}
