package br.com.expenseme.service;

import br.com.expenseme.model.Usuario;
import br.com.expenseme.core.EMF;
import br.com.expenseme.core.GenericCRUDService;
import java.util.List;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
public class UserService extends GenericCRUDService<Usuario> {

    private static final int FIRST_ROW = 0;

    public UserService() {
        super(Usuario.class, EMF.get().createEntityManager());
    }

    public Usuario lookupUser(final String username, final String passwd) {
        List<Usuario> result = findByNamedQuery(Usuario.findByUsernameAndPasswd,username, passwd);
        if (!result.isEmpty()) {
            return result.get(FIRST_ROW);
        } else {
            return null;
        }
    }

}
