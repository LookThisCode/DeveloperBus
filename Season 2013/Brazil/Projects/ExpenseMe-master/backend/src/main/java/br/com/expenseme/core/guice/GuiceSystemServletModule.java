package br.com.expenseme.core.guice;

import br.com.expenseme.boundary.ConvenioGateway;
import br.com.expenseme.boundary.DespesaGateway;
import br.com.expenseme.boundary.UserGateway;
import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public class GuiceSystemServletModule extends GuiceSystemServiceServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();
        Set<Class<?>> serviceClasses = new HashSet<>();
        serviceClasses.add(UserGateway.class);
        serviceClasses.add(DespesaGateway.class);
        serviceClasses.add(ConvenioGateway.class);
        this.serveGuiceSystemServiceServlet("/_ah/spi/*", serviceClasses);
    }

}
