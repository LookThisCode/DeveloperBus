package br.com.powerup.domain.model;

public class WorkoutSession {

	Long workoutId;
	UserProfile userProfile;
	Training trainning;
	
	public WorkoutSession(Long id, UserProfile userProfile,
			Training trainning) {
		super();
		this.workoutId = id;
		this.userProfile = userProfile;
		this.trainning = trainning;
	}

	public Long getWorkoutId() {
		return workoutId;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public Training getTrainning() {
		return trainning;
	}
}
