package br.com.powerup.api;

import java.util.logging.Logger;

import javax.inject.Inject;

import br.com.powerup.domain.model.LogWorkRequest;
import br.com.powerup.domain.model.LogWorkResponse;
import br.com.powerup.domain.model.NewWorkoutRequest;
import br.com.powerup.domain.model.PowerUp;
import br.com.powerup.domain.model.SignupRequest;
import br.com.powerup.domain.model.UserProfile;
import br.com.powerup.domain.model.WorkoutSession;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;

@Api(name = "powerup", version = "v1", description = "PowerUp Workout API", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
		com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID, Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
public class PowerUpApiV3  {

	private static final Logger LOGGER = Logger.getLogger(PowerUpApiV3.class.getName());
	
	private PowerUp powerUp;

	@Inject
	public PowerUpApiV3(PowerUp powerUp) {
		super();
		this.powerUp = powerUp;
	}

	public UserProfile signUp(SignupRequest signupRequest) {
		return powerUp.signUp(signupRequest);
	}
	
	public WorkoutSession startNewWorkout(NewWorkoutRequest newWorkoutRequest) throws NotFoundException, ForbiddenException {
		try {
			LOGGER.info(String.format(
					"Starting new workout for user: %s", newWorkoutRequest.getUserId()));
			return powerUp.startNewWorkout(newWorkoutRequest);
		} catch (com.googlecode.objectify.NotFoundException e) {
			throw new com.google.api.server.spi.response.NotFoundException(e);
		}
	}

	public LogWorkResponse logWork(LogWorkRequest logWorkRequest) throws ForbiddenException {
		return powerUp.logWork(logWorkRequest);
	}
	
}