package com.ydahar.sbk.service;

import com.ydahar.sbk.domain.*; // for static metamodels
import com.ydahar.sbk.domain.Event;
import com.ydahar.sbk.repository.EventRepository;
import com.ydahar.sbk.service.criteria.EventCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Event} entities in the database.
 * The main input is a {@link EventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Event} or a {@link Page} of {@link Event} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventQueryService extends QueryService<Event> {

    private final Logger log = LoggerFactory.getLogger(EventQueryService.class);

    private final EventRepository eventRepository;

    public EventQueryService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Return a {@link List} of {@link Event} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Event> findByCriteria(EventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Event> specification = createSpecification(criteria);
        return eventRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Event} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Event> findByCriteria(EventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Event> specification = createSpecification(criteria);
        return eventRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Event> specification = createSpecification(criteria);
        return eventRepository.count(specification);
    }

    /**
     * Function to convert {@link EventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Event> createSpecification(EventCriteria criteria) {
        Specification<Event> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Event_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Event_.title));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Event_.date));
            }
            if (criteria.getStartHour() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartHour(), Event_.startHour));
            }
            if (criteria.getEndHour() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndHour(), Event_.endHour));
            }
            if (criteria.getPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriod(), Event_.period));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Event_.price));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Event_.type));
            }
            if (criteria.getIsLive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsLive(), Event_.isLive));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Event_.country));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Event_.address));
            }
            if (criteria.getAddLat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddLat(), Event_.addLat));
            }
            if (criteria.getAddLong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddLong(), Event_.addLong));
            }
            if (criteria.getImage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImage(), Event_.image));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), Event_.link));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Event_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Event_.email));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Event_.description));
            }
        }
        return specification;
    }
}
