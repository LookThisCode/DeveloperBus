package br.com.expenseme.boundary;

import br.com.expenseme.model.Despesa;
import br.com.expenseme.service.DespesaService;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.io.Serializable;
import java.util.Collection;
import javax.inject.Named;

/**
 * Gateway de despesas
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
@Api(name = "despesa",
        version = "v1",
        description = "Gateway de despesas")
public class DespesaGateway implements Serializable {

    private DespesaService despesaService;

    public DespesaGateway() {
        this.despesaService = new DespesaService();
    }

    @ApiMethod(
            name = "despesa.registrar",
            path = "despesa",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public void registerExpense(Despesa despesa) throws BadRequestException {
        despesaService.persist(despesa);
    }

    @ApiMethod(
            name = "despesa.listar",
            path = "despesa/list",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public Collection<Despesa> listExpenses() {
        return despesaService.findAll();
    }

    @ApiMethod(
            name = "despesa.update",
            path = "despesa/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public Key updateExpense(@Named("id") Long id, Despesa despesa) {
        Key key = KeyFactory.createKey(Despesa.class.getSimpleName(), id);
        return despesa.getId();
    }
}
