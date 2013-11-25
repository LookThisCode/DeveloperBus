package br.com.expenseme.core.guice;

import br.com.expenseme.service.ConvenioService;
import br.com.expenseme.service.DespesaService;
import br.com.expenseme.service.UserService;
import com.google.inject.AbstractModule;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public class AppGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).to(UserService.class);
        bind(ConvenioService.class).to(ConvenioService.class);
        bind(DespesaService.class).to(DespesaService.class);
    }

}
