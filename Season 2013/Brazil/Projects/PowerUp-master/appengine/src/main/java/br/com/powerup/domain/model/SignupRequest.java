package br.com.powerup.domain.model;

public class SignupRequest {
	
	public String userId;
	public Double weight;
	public Double height;
	public String name;
	public String imageUrl;
	public String gymId;

	public SignupRequest() {
		super();
	}

	public SignupRequest(String userId, Double weight, Double height,
			String name, String gymId) {
		this.userId = userId;
		this.weight = weight;
		this.height = height;
		this.name = name;
		this.gymId = gymId;
	}

	public String getUserId() {
		return userId;
	}

	public Double getWeight() {
		return weight;
	}

	public Double getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getGymId() {
		return gymId;
	}
}