package br.com.expenseme.core.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public class GuiceContextListener extends GuiceServletContextListener{

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceSystemServletModule());
    }

}
