package com.devbusbrasil.powerup;

import com.devbusbrasil.powerup.lib.QRCode;
import com.devbusbrasil.powerup.lib.TrainingItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExerciseDetail extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
        TrainingItem trainingItem = QRCode.GetInstance().getTrainingItem();
        QRCode.GetInstance().setCode(null);
        
        if (trainingItem != null) {
        	loadData(trainingItem);
        }
        else {
        	Toast.makeText(getApplicationContext(), "QR n‹o reconhecido, tente novamente!",
					   Toast.LENGTH_LONG).show();
        	finish();
        }
    }
    
    private void loadData(TrainingItem pTrainingItem) {
    	
    	TextView title = (TextView)findViewById(R.id.detailTitle);
        title.setText(pTrainingItem.getName());
    	
    	TextView text = (TextView)findViewById(R.id.detailText);
        text.setText(pTrainingItem.getText());
    	
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
		ImageView image = (ImageView)Home.GetInstance().findViewById(R.id.achievement);
		image.setVisibility(View.VISIBLE);
    }

}
