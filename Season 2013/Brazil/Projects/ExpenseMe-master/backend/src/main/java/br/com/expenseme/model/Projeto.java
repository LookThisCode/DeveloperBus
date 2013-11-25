package br.com.expenseme.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
@Entity
public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private Cliente empresa;
    private String name;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public Cliente getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Cliente empresa) {
        this.empresa = empresa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
