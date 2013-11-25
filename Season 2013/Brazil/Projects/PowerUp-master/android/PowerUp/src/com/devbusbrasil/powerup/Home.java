package com.devbusbrasil.powerup;

import com.devbusbrasil.powerup.lib.NetworkManager;
import com.devbusbrasil.powerup.lib.QRCode;
import com.devbusbrasil.powerup.lib.Training;
import com.devbusbrasil.powerup.lib.TrainingItem;
import com.devbusbrasil.powerup.lib.User;
import com.devbusbrasil.zbar.CameraTestActivity;
import com.google.android.gms.plus.PlusClient;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity implements View.OnClickListener {

	private static Home mInstance;
	public static Home GetInstance() {
		return mInstance;
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstance = this;
        
        try {
	        NetworkManager.GetInstance().UpdateTraining();
	        updateScrollView();
	        updateSocialInfo();
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error connection to Google+",
					   Toast.LENGTH_LONG).show();
		}
        
        // camera
        findViewById(R.id.buttonCamera).setOnClickListener(this);
        
        // achievement
        findViewById(R.id.achievement).setOnClickListener(this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("POWERUP", "QRCode = " + QRCode.GetInstance().getCode());
        
        if (QRCode.GetInstance().getCode() != null) {
	        Intent i = new Intent(this, ExerciseDetail.class);
	        startActivity(i);
        }
    }

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.buttonCamera ) {
	        Intent i = new Intent(this, CameraTestActivity.class);
	        startActivity(i);
		}
		else if (view.getId() == R.id.achievement) {
			ImageView image = (ImageView)findViewById(R.id.achievement);
			image.setVisibility(View.GONE);
		}
	}
	
	private void updateScrollView() {

		TableLayout ll = (TableLayout) findViewById(R.id.tableLayoutList);
        View mTableRow = null;

		for (int i = 0; i < Training.GetInstance().getTrainingList().size(); i++) {
			TrainingItem trainingItem = (TrainingItem)Training.GetInstance().getTrainingList().get(i);

			mTableRow = (RelativeLayout) View.inflate(this, R.layout.view_item, null);
			
        	TextView tv = (TextView)mTableRow.findViewById(R.id.myImageViewText);
            tv.setText(trainingItem.getName());
            
            TextView duration = (TextView)mTableRow.findViewById(R.id.trainingDuration);
            duration.setText("" + trainingItem.getDuration() + " s");
            
            TextView weight = (TextView)mTableRow.findViewById(R.id.trainingWeight);
            weight.setText("" + trainingItem.getWeight() + " kg");
            
            TextView cycle = (TextView)mTableRow.findViewById(R.id.trainingCycle);
            cycle.setText("" + trainingItem.getRepeate() + " x");
            
            mTableRow.setTag(i);
            ll.addView(mTableRow);
		}
		
	}
	
	private void updateSocialInfo() {

        PlusClient plusClient = User.GetInstance().getPlusClient();
        
        if (plusClient != null) {
        	TextView tv = (TextView)findViewById(R.id.gplusName);
            tv.setText(plusClient.getCurrentPerson().getDisplayName());
            
            TextView level = (TextView)findViewById(R.id.userLevel);
            level.setText("NêVEL " + User.GetInstance().getLevel());
            
            TextView training = (TextView)findViewById(R.id.userTraining);
            training.setText(Training.GetInstance().getCompleted() + " / " +
            		Training.GetInstance().getTrainingList().size() + " EXERCêCIOS");
            
            ImageButton ib = (ImageButton)findViewById(R.id.gplusImage);
            Picasso.with(this)
            	.load(plusClient.getCurrentPerson().getImage().getUrl())
            	.resize(200, 200)
            	.into(ib);
        }
        
	}
}