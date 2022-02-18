package com.ydahar.sbk.service;

import com.ydahar.sbk.domain.City;
import com.ydahar.sbk.domain.Event;
import com.ydahar.sbk.domain.Event_;
import com.ydahar.sbk.repository.EventRepository;
import com.ydahar.sbk.service.criteria.EventCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.criteria.*;
import java.util.List;

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

            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Event_.date));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Event_.type));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Event_.country));
            }
            if (criteria.getZipCode() != null) {
                specification = specification.and( jointCity(criteria.getZipCode()));
            }
            if (criteria.getDepartementCode() != null) {
                specification = specification.and( joinDepartement(criteria.getDepartementCode()));
            }
           /* if (criteria.getAddLat() != null && criteria.getAddLong() != null) {
                specification = specification.and( buildStringSpecification(criteria.getAddLat(),criteria.getAddLong(),48.936741,2.055970));
            }*/


            /*if (criteria.getAddLat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddLat(), Event_.addLat));
            }
            if (criteria.getAddLong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddLong(), Event_.addLong));
            }*/
        }
        return specification;
    }



    public static Specification<Event> jointCity(String zipCode) {
        return new Specification<Event>() {
            public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Event,City> city = root.join("city");
                 return cb.equal(city.get("zipCode"), zipCode);
            }
        };
    }

    public static Specification<Event> joinDepartement(String departementCode) {
        return new Specification<Event>() {
            public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Event,City> city = root.join("city");
                return cb.equal(city.get("departementCode"), departementCode);
            }
        };
    }


}
