package br.com.powerup.domain.model;

import java.util.List;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

public interface TrainingRepository {

	Training get(User user);
	
	List<Training> list(User user);
	
	Key<Training> save(Training treino);

	Training get(String userId);
	
}
