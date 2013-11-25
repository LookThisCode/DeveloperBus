package br.com.expenseme.service;

import br.com.expenseme.model.Convenio;
import br.com.expenseme.core.EMF;
import br.com.expenseme.core.GenericCRUDService;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public class ConvenioService extends GenericCRUDService<Convenio> {

    public ConvenioService() {
        super(Convenio.class, EMF.get().createEntityManager());
    }

}
