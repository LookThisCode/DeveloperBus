package com.sothree.slidinguppanel.demo;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class DemoActivity extends Activity {
	
	ImageView btn_registro, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_demo);
        
        btn_registro = (ImageView) findViewById(R.id.logo);
        
        btn_registro.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), MainProveedores.class);
		        startActivity(intent);
				/*Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);*/
				
			}
		});
        
        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
        layout.setAnchorPoint(0.3f);
        layout.setPanelSlideListener(new PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset < 0.2) {
                    if (getActionBar().isShowing()) {
                        getActionBar().hide();
                    }
                } else {
                    if (!getActionBar().isShowing()) {
                        getActionBar().show();
                    }
                }
            }

            @Override
            public void onPanelExpanded(View panel) {


            }

            @Override
            public void onPanelCollapsed(View panel) {


            }

            @Override
            public void onPanelAnchored(View panel) {


            }
        });
        TextView t = (TextView) findViewById(R.id.brought_by);
        t.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        return true;
    }

}
