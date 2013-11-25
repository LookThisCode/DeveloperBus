package br.com.expenseme.core;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public final class EMF {

    private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("gae-test");

    private EMF() {
    }

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}
