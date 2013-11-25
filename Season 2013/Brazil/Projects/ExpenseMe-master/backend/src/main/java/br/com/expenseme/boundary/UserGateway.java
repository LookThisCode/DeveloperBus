package br.com.expenseme.boundary;

import br.com.expenseme.model.Usuario;
import br.com.expenseme.service.UserService;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Collection;
import javax.inject.Named;

/**
 * User Gateway
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
@Api(name = "user",
        version = "v1",
        description = "Controle de acesso dos usuários")
public class UserGateway implements Serializable {

    private UserService userService;

    public UserGateway() {
        this.userService = new UserService();
    }

    @ApiMethod(
            name = "user.authentication",
            path = "user/auth",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public Usuario auth(@Named("username") String username, @Named("password") String password) throws UnauthorizedException {
        Usuario lookupUser = userService.lookupUser(username, password);
        if (lookupUser == null) {
            throw new UnauthorizedException("Username or password is invalid");
        } else {
            return lookupUser;
        }
    }

    @ApiMethod(
            name = "user.register",
            path = "user",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public Key registerUser(Usuario user) {
        userService.persist(user);
        return user.getId();

    }

    @ApiMethod(
            name = "user.list",
            path = "user/list",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public Collection<Usuario> listUser() {
        return userService.findAll();
    }

    @ApiMethod(
            name = "user.update",
            path = "user/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public void updateUser(@Named("id") Long id) {
        System.out.println("Update: " + id);
    }

    @ApiMethod(
            name = "user.delete",
            path = "user/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public Key deleteUser(Key key) {
        Usuario user = userService.findById(key);
        userService.remove(user);
        return key;
    }
}
