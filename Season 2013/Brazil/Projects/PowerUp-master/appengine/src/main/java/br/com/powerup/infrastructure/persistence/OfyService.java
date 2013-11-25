package br.com.powerup.infrastructure.persistence;

import br.com.powerup.domain.model.ExerciseDescriptor;
import br.com.powerup.domain.model.Score;
import br.com.powerup.domain.model.Training;
import br.com.powerup.domain.model.UserProfile;
import br.com.powerup.domain.model.Worklog;
import br.com.powerup.domain.model.Workout;
import br.com.powerup.domain.model.WorkoutDevice;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

	static {
        factory().register(Training.class);
        factory().register(ExerciseDescriptor.class);
        factory().register(UserProfile.class);
        factory().register(Worklog.class);
        factory().register(Score.class);
        factory().register(Workout.class);
        factory().register(WorkoutDevice.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
    
}