package com.devbusbrasil.powerup.lib;

import java.io.IOException;
import java.util.List;

import android.os.StrictMode;
import android.util.Log;

import com.appspot.powerupbeta.powerup.Powerup;
import com.appspot.powerupbeta.powerup.model.Exercise;
import com.appspot.powerupbeta.powerup.model.NewWorkoutRequest;
import com.appspot.powerupbeta.powerup.model.UserProfile;
import com.appspot.powerupbeta.powerup.model.WorkoutSession;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class NetworkManager {

	private static NetworkManager mInstance;
	public static NetworkManager GetInstance() {
		if (mInstance == null) {
			mInstance = new NetworkManager();
		}
		return mInstance;
	}
	
	public NetworkManager() {
		
	}
	
	public boolean UpdateTraining() {

        //TODO: workaround
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //SharedPreferences settings = MainActivity.GetInstance().getSharedPreferences("PowerUp", 0);
        //GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(MainActivity.GetInstance(),
        //   "server:client_id:19047933307-2p09orqqule35mjcjkvs34s7di7jldi5.apps.googleusercontent.com");        
        
        try {
        	Powerup.Builder builder = new Powerup.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        	Powerup service = builder.build();

        	NewWorkoutRequest request = new NewWorkoutRequest();
        	request.setUserId(User.GetInstance().getPlusClient().getCurrentPerson().getId());
        	
        	WorkoutSession workout = service.powerUpApiV3().startNewWorkout(request).execute();
        	List<Exercise> list = workout.getTrainning().getExercicios();
        	
        	UserProfile user = workout.getUserProfile();        	
        	User.GetInstance().setId(user.getId().toString());
        	User.GetInstance().setName(user.getName());
        	User.GetInstance().setImageUrl(user.getImageUrl());
        	User.GetInstance().setLevel(user.getLevel());
			
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {				
					Exercise exercise = (Exercise)list.get(i);
					//Log.w("POWERUP", "LIST REAL [" + i + "] = " + exercise.getWorkoutDeviceId());
					
					Training.GetInstance().addExercise(new TrainingItem(exercise.getId(), 
							exercise.getName(), exercise.getDescription(), exercise.getImageUrl(), exercise.getNumeroDeRepeticoes(),
							exercise.getSerie(), exercise.getDescanso(), exercise.getCarga(), exercise.getWorkoutDeviceId(), exercise.getDuracao()));
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
			for (int i = 0; i < 10; i++) {				
				TrainingItem trainingItem = new TrainingItem();
				trainingItem.setName("Exercicio " + i);
				trainingItem.setId("" + i);
				//Log.w("POWERUP", "LIST FAKE [" + i + "] = " + trainingItem.getName());
				Training.GetInstance().addExercise(trainingItem);
			}
			return false;
		}
		
		return true;
		
	}
	
}
