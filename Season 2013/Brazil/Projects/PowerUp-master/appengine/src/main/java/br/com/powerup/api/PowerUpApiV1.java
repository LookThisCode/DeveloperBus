package br.com.powerup.api;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import br.com.powerup.domain.model.Exercise;
import br.com.powerup.domain.model.ExerciseDescriptor;
import br.com.powerup.domain.model.Training;
import br.com.powerup.domain.model.TrainingRepository;
import br.com.powerup.infrastructure.persistence.OfyService;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

@Api(name = "treino", version = "v1", description = "Trainning log API", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
		com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID, Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
@Deprecated
public class PowerUpApiV1 {

	private static final Logger LOGGER = Logger.getLogger(PowerUpApiV1.class.getName());

	protected TrainingRepository treinoRepository;

	@Inject
	public PowerUpApiV1(TrainingRepository treinoRepository) {
		super();
		this.treinoRepository = treinoRepository;
	}

	public Training getTreino(User user) throws OAuthRequestException,
			IOException {
		if (user == null) {
			throw new OAuthRequestException("Forbidden");
		}
		LOGGER.info(String.format("userId: %s", user.getUserId()));
		return treinoRepository.get(user);
	}

	public void saveTreino(User user, Training treino)
			throws OAuthRequestException, IOException {
		treino.user(user);
		treinoRepository.save(treino);
	}

	@ApiMethod(name = "populate", path = "populate", httpMethod = HttpMethod.GET)
	public void populate() {
		
		List<ExerciseDescriptor> all = OfyService.ofy().load().type(ExerciseDescriptor.class).list();
		
		LOGGER.info(String.format("Found %s descriptors.", all.size()));
		
		Training treino = new Training(new User("bruno@powerup.com", "powerup.com", "104247733575251837151"));
		for (ExerciseDescriptor exerciseDescriptor : all) {
			treino.adicionaExercicio(new Exercise(exerciseDescriptor));	
		}
		treinoRepository.save(treino);
		
		treino = new Training(new User("fabio@powerup.com", "powerup.com", "111"));
		for (ExerciseDescriptor exerciseDescriptor : all) {
			treino.adicionaExercicio(new Exercise(exerciseDescriptor));	
		}
		
		treinoRepository.save(treino);

		treino = new Training(new User("andre@powerup.com", "powerup.com", "222"));
		for (ExerciseDescriptor exerciseDescriptor : all) {
			treino.adicionaExercicio(new Exercise(exerciseDescriptor));	
		}
		treinoRepository.save(treino);
		
		treino = new Training(new User("dani@powerup.com", "powerup.com", "333"));
		for (ExerciseDescriptor exerciseDescriptor : all) {
			treino.adicionaExercicio(new Exercise(exerciseDescriptor));	
		}
		treinoRepository.save(treino);
	}

}