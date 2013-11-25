package br.com.powerup.infrastructure.web;

import br.com.powerup.api.GuiceSSSModule;
import br.com.powerup.domain.model.PowerUp;
import br.com.powerup.domain.model.TrainingRepository;
import br.com.powerup.infrastructure.DefaultPowerUp;
import br.com.powerup.infrastructure.persistence.ObjectifyTrainingRepository;
import br.com.powerup.infrastructure.persistence.Transact;
import br.com.powerup.infrastructure.persistence.TransactInterceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.googlecode.objectify.ObjectifyFilter;
import com.sun.jersey.guice.JerseyServletModule;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
    	
    	return Guice.createInjector(new GuiceSSSModule(), new JerseyServletModule() {

            @Override
            protected void configureServlets() {
            	bind(ObjectifyFilter.class).in(Scopes.SINGLETON);
            	filter("/*").through(ObjectifyFilter.class);
        		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transact.class), new TransactInterceptor());
                bind(TrainingRepository.class).to(ObjectifyTrainingRepository.class);
                bind(PowerUp.class).to(DefaultPowerUp.class);
            }
        });
    }
}
