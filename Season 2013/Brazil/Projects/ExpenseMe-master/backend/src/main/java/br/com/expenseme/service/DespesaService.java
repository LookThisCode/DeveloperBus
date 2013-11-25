package br.com.expenseme.service;

import br.com.expenseme.model.Despesa;
import br.com.expenseme.core.EMF;
import br.com.expenseme.core.GenericCRUDService;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public class DespesaService extends GenericCRUDService<Despesa> {

    public DespesaService() {
        super(Despesa.class, EMF.get().createEntityManager());
    }

}
