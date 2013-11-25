package br.com.powerup.domain.model;

import static br.com.powerup.infrastructure.persistence.OfyService.ofy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.powerup.infrastructure.DefaultPowerUp;
import br.com.powerup.infrastructure.persistence.ObjectifyTrainingRepository;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.sandbox.infrastructure.persistence.TrainingFixture;

public class PowerUpTest {

	final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig());
	
	private TrainingFixture trainingFixture;

	private PowerUp powerUp;
	
	@Before
	public void helperSetUp() {
		helper.setUp();		
		trainingFixture = new TrainingFixture();
		powerUp = new DefaultPowerUp(new ObjectifyTrainingRepository());
	}

	@After
	public void helperTearDown() {
		helper.tearDown();
	}
	
	@Test
	public void logWorkShouldIncrementUserScore() {
		Training t = trainingFixture.training();
		String exerciseId = t.getExercicios().get(0).getId();
		String userId = TrainingFixture.U.getUserId();;
		Long trainingId = t.getId();
		LogWorkResponse resp = powerUp.logWork(new LogWorkRequest(exerciseId, userId, trainingId));
		
		assertEquals(Integer.valueOf(10), resp.getPointsEarned());
		
		List<Worklog> logs = ofy().load().type(Worklog.class).ancestor(KeyFactory.createKey(Workout.class.getSimpleName(), trainingId)).list();
		assertEquals(1, logs.size());

		assertNotNull(logs.get(0).getOccurredOn());
		
		Score score = score(userId);
		assertNotNull(score);
		assertEquals(110, score.getTotalPoints().intValue());
	}
	
	@Test
	public void startNewWorkoutShouldBeComplete() {
		WorkoutSession session = powerUp.startNewWorkout(new NewWorkoutRequest(TrainingFixture.U.getUserId()));
		assertNotNull(session);
		assertNotNull(session.getUserProfile());
		assertNotNull(session.getTrainning());
		assertNotNull(session.getWorkoutId());
	}
	
	@Test
	public void scoringTests() {
		Training t = trainingFixture.training();
		String userId = TrainingFixture.U.getUserId();;
		Long trainingId = t.getId();
		
		LogWorkResponse resp = null;
		for (Exercise e : t.getExercicios()) {
			resp = powerUp.logWork(new LogWorkRequest(e.getId(), userId, trainingId));
		}
		
		assertEquals(Integer.valueOf(60), resp.getPointsEarned());

		Score score = score(userId);
		assertEquals(Long.valueOf(80), score.getTotalPoints());
		assertNotNull(score);

		UserProfile userProfile = userProfile(userId);
		assertEquals(Level.ONE, userProfile.getLevel());

	}
	
	@Test
	public void levelUpgrade() {
		Training t = trainingFixture.training();
		String userId = TrainingFixture.U.getUserId();;
		Long trainingId = t.getId();
		
		LogWorkResponse resp = null;
		for (Exercise e : t.getExercicios()) {
			resp = powerUp.logWork(new LogWorkRequest(e.getId(), userId, trainingId));
			resp = powerUp.logWork(new LogWorkRequest(e.getId(), userId, trainingId));
		}
		
		Score score = score(userId);
		System.out.println(score.getTotalPoints());
		UserProfile userProfile = userProfile(userId);
		assertEquals(Level.TWO, userProfile.getLevel());
	}
	
	private Score score(String userId) {
		return ofy().load().type(Score.class).ancestor(KeyFactory.createKey(User.class.getSimpleName(), userId)).first().safe();
	}
	
	private UserProfile userProfile(String userId) {
		return ofy().load().type(UserProfile.class)
				.ancestor(userParentKey(userId)).first().safe();
	}
	
	private com.google.appengine.api.datastore.Key userParentKey(String userId) {
		return KeyFactory.createKey(User.class.getSimpleName(), userId);
	}

}