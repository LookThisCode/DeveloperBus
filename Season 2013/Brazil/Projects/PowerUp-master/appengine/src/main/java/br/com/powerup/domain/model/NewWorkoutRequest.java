package br.com.powerup.domain.model;

public class NewWorkoutRequest {
	
	private String userId;

	public NewWorkoutRequest() {
		super();
	}

	public NewWorkoutRequest(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}