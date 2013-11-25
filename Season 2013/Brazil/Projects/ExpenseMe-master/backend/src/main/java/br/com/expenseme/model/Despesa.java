package br.com.expenseme.model;

import java.io.Serializable;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author <a href="mailto:pviniciusfm@gmail.com">Paulo Vinicius F. Machado</a>
 */
@Entity
public class Despesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private String location;
    /**
     * Valor será armazenado em long pois se fosse double após manipulações
     * poderia perder precisão No futuro trocaremos por joda-money
     */
    private Long valor;
    private String descricao;
    private CategoriaDespesa categoria;
    @Basic
    private Blob comprovante;
    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    private Date dateTime;
    private Boolean billable;
    private Boolean reimbursable;

    public Despesa() {
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaDespesa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDespesa categoria) {
        this.categoria = categoria;
    }

    public Blob getComprovante() {
        return comprovante;
    }

    public void setComprovante(Blob comprovante) {
        this.comprovante = comprovante;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean isBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Boolean isReimbursable() {
        return reimbursable;
    }

    public void setReimbursable(Boolean reimbursable) {
        this.reimbursable = reimbursable;
    }

}
