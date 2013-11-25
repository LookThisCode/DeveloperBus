package com.google.sandbox.infrastructure.persistence;

import static br.com.powerup.infrastructure.persistence.OfyService.ofy;
import br.com.powerup.domain.model.Exercise;
import br.com.powerup.domain.model.ExerciseDescriptor;
import br.com.powerup.domain.model.Training;
import br.com.powerup.domain.model.UserProfile;

import com.google.appengine.api.users.User;

public class TrainingFixture {

	static final ExerciseDescriptor LEG_PRESS = new ExerciseDescriptor(
	"Leg press",
	"perna",
	"O Leg Press é um aparelho que tem como objetivo principal auxiliar nos exercícios de perna, focando nos músculos quadrícepe, posicionando os pés no centro da placa à frente com as pernas dobradas, e panturrilha, posicionando apenas as pontas dos pés com a perna levemente semi-flexionada. É importante ter um cuidado especial para não deixar que a linha do joelho ultrapasse a linha do dedão do pé, a não ser que determinado pelo instrutor.",
	"http://www.laimisenergy.com/wp-content/uploads/2011/09/leg-press-11.jpg");
	static final ExerciseDescriptor SUPINO_RETO = new ExerciseDescriptor(
	"Leg press",
	"perna",
	"O Leg Press é um aparelho que tem como objetivo principal auxiliar nos exercícios de perna, focando nos músculos quadrícepe, posicionando os pés no centro da placa à frente com as pernas dobradas, e panturrilha, posicionando apenas as pontas dos pés com a perna levemente semi-flexionada. É importante ter um cuidado especial para não deixar que a linha do joelho ultrapasse a linha do dedão do pé, a não ser que determinado pelo instrutor.",
	"http://www.laimisenergy.com/wp-content/uploads/2011/09/leg-press-11.jpg");
	static final ExerciseDescriptor EX3 = new ExerciseDescriptor(
	"Leg press",
	"perna",
	"O Leg Press é um aparelho que tem como objetivo principal auxiliar nos exercícios de perna, focando nos músculos quadrícepe, posicionando os pés no centro da placa à frente com as pernas dobradas, e panturrilha, posicionando apenas as pontas dos pés com a perna levemente semi-flexionada. É importante ter um cuidado especial para não deixar que a linha do joelho ultrapasse a linha do dedão do pé, a não ser que determinado pelo instrutor.",
	"http://www.laimisenergy.com/wp-content/uploads/2011/09/leg-press-11.jpg");
	
	public static final User U = new User("f@f.com", "fff", "123");
	public static final UserProfile PROFILE = new UserProfile(U.getUserId());
	private Training training;
	
	public TrainingFixture() {
		ofy().save().entity(TrainingFixture.LEG_PRESS).now();
		ofy().save().entity(TrainingFixture.EX3).now();
		ofy().save().entity(TrainingFixture.SUPINO_RETO).now();
		ofy().save().entity(PROFILE).now();
		training = new Training(TrainingFixture.U);
		training.adicionaExercicio(new Exercise(TrainingFixture.LEG_PRESS));
		training.adicionaExercicio(new Exercise(TrainingFixture.EX3));
		training.adicionaExercicio(new Exercise(TrainingFixture.SUPINO_RETO));
		ofy().save().entity(training).now();
	}
	
	
	public Training training() {
		return training;
	}
	
	
}