package br.com.expenseme.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
@Entity
@NamedQueries(
        @NamedQuery(name = Usuario.findByUsernameAndPasswd, query = "SELECT u FROM Usuario u WHERE u.login = ?1 and u.senha = ?2"))
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String findByUsernameAndPasswd = "Usuario.findByUsernameAndPasswd";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private String login;
    private String senha;
    private String oauthToken;

    public Usuario() {
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

}
