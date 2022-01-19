package com.ydahar.sbk.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

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

    private LongFilter id;

    private StringFilter title;

    private LocalDateFilter date;

    private StringFilter startHour;

    private StringFilter endHour;

    private StringFilter period;

    private DoubleFilter price;

    private StringFilter type;

    private BooleanFilter isLive;

    private StringFilter country;

    private StringFilter address;

    private StringFilter addLat;

    private StringFilter addLong;

    private StringFilter image;

    private StringFilter link;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter description;

    private Boolean distinct;

    public EventCriteria() {}

    public EventCriteria(EventCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.startHour = other.startHour == null ? null : other.startHour.copy();
        this.endHour = other.endHour == null ? null : other.endHour.copy();
        this.period = other.period == null ? null : other.period.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.isLive = other.isLive == null ? null : other.isLive.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.addLat = other.addLat == null ? null : other.addLat.copy();
        this.addLong = other.addLong == null ? null : other.addLong.copy();
        this.image = other.image == null ? null : other.image.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventCriteria copy() {
        return new EventCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
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

    public StringFilter getStartHour() {
        return startHour;
    }

    public StringFilter startHour() {
        if (startHour == null) {
            startHour = new StringFilter();
        }
        return startHour;
    }

    public void setStartHour(StringFilter startHour) {
        this.startHour = startHour;
    }

    public StringFilter getEndHour() {
        return endHour;
    }

    public StringFilter endHour() {
        if (endHour == null) {
            endHour = new StringFilter();
        }
        return endHour;
    }

    public void setEndHour(StringFilter endHour) {
        this.endHour = endHour;
    }

    public StringFilter getPeriod() {
        return period;
    }

    public StringFilter period() {
        if (period == null) {
            period = new StringFilter();
        }
        return period;
    }

    public void setPeriod(StringFilter period) {
        this.period = period;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public DoubleFilter price() {
        if (price == null) {
            price = new DoubleFilter();
        }
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
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

    public BooleanFilter getIsLive() {
        return isLive;
    }

    public BooleanFilter isLive() {
        if (isLive == null) {
            isLive = new BooleanFilter();
        }
        return isLive;
    }

    public void setIsLive(BooleanFilter isLive) {
        this.isLive = isLive;
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

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getAddLat() {
        return addLat;
    }

    public StringFilter addLat() {
        if (addLat == null) {
            addLat = new StringFilter();
        }
        return addLat;
    }

    public void setAddLat(StringFilter addLat) {
        this.addLat = addLat;
    }

    public StringFilter getAddLong() {
        return addLong;
    }

    public StringFilter addLong() {
        if (addLong == null) {
            addLong = new StringFilter();
        }
        return addLong;
    }

    public void setAddLong(StringFilter addLong) {
        this.addLong = addLong;
    }

    public StringFilter getImage() {
        return image;
    }

    public StringFilter image() {
        if (image == null) {
            image = new StringFilter();
        }
        return image;
    }

    public void setImage(StringFilter image) {
        this.image = image;
    }

    public StringFilter getLink() {
        return link;
    }

    public StringFilter link() {
        if (link == null) {
            link = new StringFilter();
        }
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
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
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(date, that.date) &&
            Objects.equals(startHour, that.startHour) &&
            Objects.equals(endHour, that.endHour) &&
            Objects.equals(period, that.period) &&
            Objects.equals(price, that.price) &&
            Objects.equals(type, that.type) &&
            Objects.equals(isLive, that.isLive) &&
            Objects.equals(country, that.country) &&
            Objects.equals(address, that.address) &&
            Objects.equals(addLat, that.addLat) &&
            Objects.equals(addLong, that.addLong) &&
            Objects.equals(image, that.image) &&
            Objects.equals(link, that.link) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(description, that.description) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            date,
            startHour,
            endHour,
            period,
            price,
            type,
            isLive,
            country,
            address,
            addLat,
            addLong,
            image,
            link,
            phone,
            email,
            description,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (startHour != null ? "startHour=" + startHour + ", " : "") +
            (endHour != null ? "endHour=" + endHour + ", " : "") +
            (period != null ? "period=" + period + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (isLive != null ? "isLive=" + isLive + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (addLat != null ? "addLat=" + addLat + ", " : "") +
            (addLong != null ? "addLong=" + addLong + ", " : "") +
            (image != null ? "image=" + image + ", " : "") +
            (link != null ? "link=" + link + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
