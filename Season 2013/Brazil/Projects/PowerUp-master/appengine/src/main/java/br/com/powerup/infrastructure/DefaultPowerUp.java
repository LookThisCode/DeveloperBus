package br.com.powerup.infrastructure;

import static br.com.powerup.infrastructure.persistence.OfyService.ofy;

import java.util.List;

import javax.inject.Inject;

import br.com.powerup.domain.model.Badge;
import br.com.powerup.domain.model.LogWorkRequest;
import br.com.powerup.domain.model.LogWorkResponse;
import br.com.powerup.domain.model.NewWorkoutRequest;
import br.com.powerup.domain.model.PowerUp;
import br.com.powerup.domain.model.Score;
import br.com.powerup.domain.model.SignupRequest;
import br.com.powerup.domain.model.Training;
import br.com.powerup.domain.model.TrainingRepository;
import br.com.powerup.domain.model.UserProfile;
import br.com.powerup.domain.model.Worklog;
import br.com.powerup.domain.model.Workout;
import br.com.powerup.domain.model.WorkoutSession;
import br.com.powerup.infrastructure.persistence.Transact;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.TxnType;

public class DefaultPowerUp implements PowerUp {

	private static final int POINTS_PER_WORKLOG = 10;
	private TrainingRepository trainingRepository;

	@Inject
	public DefaultPowerUp(TrainingRepository trainingRepository) {
		super();
		this.trainingRepository = trainingRepository;
	}

	private Training training(String userId) {
		return trainingRepository.get(userId);
	}

	@Override
	@Transact(TxnType.REQUIRED)
	public LogWorkResponse logWork(LogWorkRequest logWorkRequest) {

		int pointsEarned = POINTS_PER_WORKLOG;
		Worklog worklog = new Worklog(logWorkRequest.getExerciseId(),
				logWorkRequest.getTrainingId());
		ofy().save().entity(worklog);

		List<Worklog> logs = logs(logWorkRequest);

		Training training = training(logWorkRequest.getUserId());
		if (logs.size() == training.totalExercises()) {
			pointsEarned += 50;
		}

		Score score = score(logWorkRequest.getUserId());
		score.increment(pointsEarned);
		ofy().save().entity(score);
		
		UserProfile userProfile = userProfile(logWorkRequest.getUserId());
		LogWorkResponse logWorkResponse = new LogWorkResponse(pointsEarned, score.getTotalPoints());
		if (isFirstWorkoutForTraining(training)) {
			userProfile.addBadge(Badge.Estreia);
			logWorkResponse.addBadge(Badge.Estreia);
		}

		if ( userProfile.adjustLevel(score) ) {
			ofy().save().entity(userProfile);
			logWorkResponse.levelUp();
		}
		
		return logWorkResponse;
	}

	private boolean isFirstWorkoutForTraining(Training training) {
		Integer count = ofy().load().type(Workout.class)
		.ancestor(KeyFactory.createKey(Training.class.getSimpleName(), training.getId()))
		.count();
		return count == 1;
	}

	private List<Worklog> logs(LogWorkRequest logWorkRequest) {
		return ofy()
				.load()
				.type(Worklog.class)
				.ancestor(
						KeyFactory.createKey(Workout.class.getSimpleName(),
								logWorkRequest.getTrainingId())).list();
	}

	private Score score(String userId) {
		try {
			Score score = ofy().load().type(Score.class)
					.ancestor(userParentKey(userId)).first().safe();
			return score;
		} catch (NotFoundException e) {
			return new Score(userId);
		}
	}

	private com.google.appengine.api.datastore.Key userParentKey(String userId) {
		return KeyFactory.createKey(User.class.getSimpleName(), userId);
	}

	@Override
	public UserProfile signUp(SignupRequest signupRequest) {
		UserProfile userProfile =  new UserProfile(signupRequest);
		ofy().save().entity(userProfile).now();
		return userProfile;
	}

	@Override
	public WorkoutSession startNewWorkout(NewWorkoutRequest newWorkoutRequest) {
		Training training = training(newWorkoutRequest.getUserId());
		UserProfile userProfile = userProfile(newWorkoutRequest);
		Key<Workout> k = ofy().save().entity(new Workout(training.getId()))
				.now();
		return new WorkoutSession(k.getId(), userProfile, training);
	}

	private UserProfile userProfile(NewWorkoutRequest newWorkoutRequest) {
		return userProfile(newWorkoutRequest.getUserId());
	}

	private UserProfile userProfile(String userId) {
		return ofy().load().type(UserProfile.class)
				.ancestor(userParentKey(userId)).first().safe();
	}

}