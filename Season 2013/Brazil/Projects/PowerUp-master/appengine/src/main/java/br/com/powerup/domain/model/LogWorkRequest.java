package br.com.powerup.domain.model;

import com.google.common.base.Preconditions;

public class LogWorkRequest {
	
	private String exerciseId;
	private String userId;
	private Long trainingId;

	public LogWorkRequest() {
		super();
	}

	public LogWorkRequest(String exerciseId, String userId, Long trainingId) {
		super();
		Preconditions.checkNotNull(exerciseId);
		Preconditions.checkNotNull(userId);
		Preconditions.checkNotNull(trainingId);
		
		this.exerciseId = exerciseId;
		this.userId = userId;
		this.trainingId = trainingId;
	}

	public String getExerciseId() {
		return exerciseId;
	}

	public String getUserId() {
		return userId;
	}

	public Long getTrainingId() {
		return trainingId;
	}
}