package br.com.powerup.domain.model;

public interface PowerUp {

	UserProfile signUp(SignupRequest signupRequest);
	
	WorkoutSession startNewWorkout(NewWorkoutRequest newWorkoutRequest);
	
	LogWorkResponse logWork(LogWorkRequest logWorkRequest);
	
}