package br.com.expenseme.boundary;

import br.com.expenseme.model.Convenio;
import br.com.expenseme.service.ConvenioService;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.util.Collection;
import javax.inject.Inject;

import javax.inject.Named;

@Api(name = "convenio",
        version = "v1",
        description = "Encontra todos convenios da empresa.")
public class ConvenioGateway {

    @Inject
    private ConvenioService convenioService;

    public ConvenioGateway() {
    }

    @ApiMethod(
            name = "convenio.list",
            path = "convenio/list",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public Collection<Convenio> list() {
        return convenioService.findAll();
    }

    @ApiMethod(
            name = "convenio.get",
            path = "convenio/{id}",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public Convenio get(@Named("id") Long id) {
        Key key = KeyFactory.createKey(Convenio.class.getSimpleName(), id);
        return convenioService.findById(key);
    }

    @ApiMethod(
            name = "convenio.add",
            path = "convenio",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public Key register(Convenio convenio) {
        convenioService.merge(convenio);
        return convenio.getId();
    }

    @ApiMethod(
            name = "convenio.delete",
            path = "convenio/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public Key delete(@Named("id") Long id) {
        Key key = KeyFactory.createKey(Convenio.class.getSimpleName(), id);
        Convenio convenio = convenioService.findById(key);
        convenioService.remove(convenio);
        return convenio.getId();
    }
}
