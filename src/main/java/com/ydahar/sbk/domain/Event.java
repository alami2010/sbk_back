package com.ydahar.sbk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_hour")
    private String startHour;

    @Column(name = "end_hour")
    private String endHour;

    @Column(name = "period")
    private String period;

    @Column(name = "price")
    private Double price;

    @Column(name = "type")
    private String type;

    @Column(name = "is_live")
    private Boolean isLive;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "add_lat")
    private String addLat;

    @Column(name = "add_long")
    private String addLong;

    @Column(name = "image")
    private String image;

    @Column(name = "link")
    private String link;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Event date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartHour() {
        return this.startHour;
    }

    public Event startHour(String startHour) {
        this.setStartHour(startHour);
        return this;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return this.endHour;
    }

    public Event endHour(String endHour) {
        this.setEndHour(endHour);
        return this;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getPeriod() {
        return this.period;
    }

    public Event period(String period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getPrice() {
        return this.price;
    }

    public Event price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return this.type;
    }

    public Event type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsLive() {
        return this.isLive;
    }

    public Event isLive(Boolean isLive) {
        this.setIsLive(isLive);
        return this;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public String getCountry() {
        return this.country;
    }

    public Event country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return this.address;
    }

    public Event address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddLat() {
        return this.addLat;
    }

    public Event addLat(String addLat) {
        this.setAddLat(addLat);
        return this;
    }

    public void setAddLat(String addLat) {
        this.addLat = addLat;
    }

    public String getAddLong() {
        return this.addLong;
    }

    public Event addLong(String addLong) {
        this.setAddLong(addLong);
        return this;
    }

    public void setAddLong(String addLong) {
        this.addLong = addLong;
    }

    public String getImage() {
        return this.image;
    }

    public Event image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return this.link;
    }

    public Event link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhone() {
        return this.phone;
    }

    public Event phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Event email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", period='" + getPeriod() + "'" +
            ", price=" + getPrice() +
            ", type='" + getType() + "'" +
            ", isLive='" + getIsLive() + "'" +
            ", country='" + getCountry() + "'" +
            ", address='" + getAddress() + "'" +
            ", addLat='" + getAddLat() + "'" +
            ", addLong='" + getAddLong() + "'" +
            ", image='" + getImage() + "'" +
            ", link='" + getLink() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
