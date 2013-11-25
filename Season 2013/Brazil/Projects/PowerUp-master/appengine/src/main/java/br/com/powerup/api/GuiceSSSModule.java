package br.com.powerup.api;

import java.util.HashSet;
import java.util.Set;

import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;

public class GuiceSSSModule extends GuiceSystemServiceServletModule {

	  @Override
	  protected void configureServlets() {
	    super.configureServlets();
	    Set<Class<?>> serviceClasses = new HashSet<Class<?>>();
	    serviceClasses.add(PowerUpApiV1.class);
	    serviceClasses.add(PowerUpApiV2.class);
	    serviceClasses.add(PowerUpApiV3.class);
	    this.serveGuiceSystemServiceServlet("/_ah/spi/*", serviceClasses);
	  }
	
}
