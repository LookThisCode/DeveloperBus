package br.com.expenseme.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Convenio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    @ManyToOne
    private Cliente empresa;

    public Convenio() {
    }

    public Convenio(Double latitude, Double longitude, String name, String description, Cliente empresa) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.empresa = empresa;
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cliente getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Cliente empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Convenio{"
                + "id=" + id
                + ", latitude=" + latitude
                + ", longitude=" + longitude
                + ", name=" + name
                + ", description=" + description + '}';
    }

}
