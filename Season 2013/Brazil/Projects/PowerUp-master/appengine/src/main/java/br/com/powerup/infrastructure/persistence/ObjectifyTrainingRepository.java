package br.com.powerup.infrastructure.persistence;

import static br.com.powerup.infrastructure.persistence.OfyService.ofy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.powerup.domain.model.Training;
import br.com.powerup.domain.model.TrainingRepository;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;


public class ObjectifyTrainingRepository implements TrainingRepository {

	@Override
	public Training get(User user) {
		return get(user.getUserId());
	}

	@Override
	public Key<Training> save(Training treino) {
		return ofy().save().entity(treino).now();
	}

	@Override
	public List<Training> list(User user) {
		List<Training> list = ofy().load()
			     .type(Training.class)
			     .ancestor(KeyFactory.createKey(parentKind(), user.getUserId()))
			     .list();
		Collections.sort(list, new Comparator<Training>() {
			@Override
			public int compare(Training o1, Training o2) {
				return o1.getCreatedOn().compareTo(o2.getCreatedOn());
			}
		});
		return list;
	}

	@Override
	public Training get(String userId) {
		return ofy().load()
			     .type(Training.class)
			     .ancestor(KeyFactory.createKey(parentKind(), userId))
			     .first().safe();
	}

	private String parentKind() {
		return User.class.getSimpleName();
	}
	
}
