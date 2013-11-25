package br.com.powerup.api;

import java.io.IOException;

import javax.inject.Inject;

import br.com.powerup.domain.model.Training;
import br.com.powerup.domain.model.TrainingRepository;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

@Api(version = "v2")
@Deprecated
public class PowerUpApiV2 extends PowerUpApiV1 {

	@Inject
	public PowerUpApiV2(TrainingRepository treinoRepository) {
		super(treinoRepository);
	}

	@Override
	public Training getTreino(User user) throws OAuthRequestException,
			IOException {
		return treinoRepository.get(new User("t@t.com", "t.com", "111"));
	}
}
