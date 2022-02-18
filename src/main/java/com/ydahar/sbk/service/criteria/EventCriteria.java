package com.ydahar.sbk.service.criteria;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.ydahar.sbk.domain.Event} entity. This class is used
 * in {@link com.ydahar.sbk.web.rest.EventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LocalDateFilter date;

    private StringFilter type;

    private StringFilter country;

    private Double addLat;

    private Double addLong;

    private String zipCode;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDepartementCode() {
        return departementCode;
    }

    public void setDepartementCode(String departementCode) {
        this.departementCode = departementCode;
    }

    private String departementCode;


    public Double getAddLat() {
        return addLat;
    }

    public void setAddLat(Double addLat) {
        this.addLat = addLat;
    }

    public Double getAddLong() {
        return addLong;
    }

    public void setAddLong(Double addLong) {
        this.addLong = addLong;
    }

    public EventCriteria() {}

    public EventCriteria(EventCriteria other) {
        this.date = other.date == null ? null : other.date.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.addLat = other.addLat == null ? null : other.addLat;
        this.addLong = other.addLong == null ? null : other.addLong;
        this.zipCode = other.zipCode == null ? null : other.zipCode;
        this.departementCode = other.departementCode == null ? null : other.departementCode;
    }

    @Override
    public EventCriteria copy() {
        return new EventCriteria(this);
    }


    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }


    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventCriteria that = (EventCriteria) o;
        return (
            Objects.equals(date, that.date) &&
            Objects.equals(type, that.type) &&
            Objects.equals(country, that.country) &&
            Objects.equals(addLat, that.addLat) &&
            Objects.equals(addLong, that.addLong)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            date,
            type,
            country,
            addLat,
            addLong
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventCriteria{" +

            (date != null ? "date=" + date + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (addLat != null ? "addLat=" + addLat + ", " : "") +
            (addLong != null ? "addLong=" + addLong + ", " : "") +
            "}";
    }
}
