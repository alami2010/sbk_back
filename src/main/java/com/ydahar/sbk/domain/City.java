package com.ydahar.sbk.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A City.
 */
@Entity
@Table(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "departement_code")
    private String departementCode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "name")
    private String name;

    @Column(name = "gps_lat")
    private Double gpsLat;

    @Column(name = "gps_lng")
    private Double gpsLng;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public City id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartementCode() {
        return this.departementCode;
    }

    public City departementCode(String departementCode) {
        this.setDepartementCode(departementCode);
        return this;
    }

    public void setDepartementCode(String departementCode) {
        this.departementCode = departementCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public City zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return this.name;
    }

    public City name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGpsLat() {
        return this.gpsLat;
    }

    public City gpsLat(Double gpsLat) {
        this.setGpsLat(gpsLat);
        return this;
    }

    public void setGpsLat(Double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public Double getGpsLng() {
        return this.gpsLng;
    }

    public City gpsLng(Double gpsLng) {
        this.setGpsLng(gpsLng);
        return this;
    }

    public void setGpsLng(Double gpsLng) {
        this.gpsLng = gpsLng;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", departementCode='" + getDepartementCode() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", name='" + getName() + "'" +
            ", gpsLat=" + getGpsLat() +
            ", gpsLng=" + getGpsLng() +
            "}";
    }
}
