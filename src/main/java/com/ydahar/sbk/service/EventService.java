package com.ydahar.sbk.service;

import com.ydahar.sbk.domain.Event;
import com.ydahar.sbk.repository.EventRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Event}.
 */
@Service
@Transactional
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Save a event.
     *
     * @param event the entity to save.
     * @return the persisted entity.
     */
    public Event save(Event event) {
        log.debug("Request to save Event : {}", event);
        return eventRepository.save(event);
    }

    /**
     * Partially update a event.
     *
     * @param event the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Event> partialUpdate(Event event) {
        log.debug("Request to partially update Event : {}", event);

        return eventRepository
            .findById(event.getId())
            .map(existingEvent -> {
                if (event.getTitle() != null) {
                    existingEvent.setTitle(event.getTitle());
                }
                if (event.getDate() != null) {
                    existingEvent.setDate(event.getDate());
                }
                if (event.getStartHour() != null) {
                    existingEvent.setStartHour(event.getStartHour());
                }
                if (event.getEndHour() != null) {
                    existingEvent.setEndHour(event.getEndHour());
                }
                if (event.getPeriod() != null) {
                    existingEvent.setPeriod(event.getPeriod());
                }
                if (event.getPrice() != null) {
                    existingEvent.setPrice(event.getPrice());
                }
                if (event.getType() != null) {
                    existingEvent.setType(event.getType());
                }
                if (event.getIsLive() != null) {
                    existingEvent.setIsLive(event.getIsLive());
                }
                if (event.getCountry() != null) {
                    existingEvent.setCountry(event.getCountry());
                }
                if (event.getAddress() != null) {
                    existingEvent.setAddress(event.getAddress());
                }
                if (event.getAddLat() != null) {
                    existingEvent.setAddLat(event.getAddLat());
                }
                if (event.getAddLong() != null) {
                    existingEvent.setAddLong(event.getAddLong());
                }
                if (event.getImage() != null) {
                    existingEvent.setImage(event.getImage());
                }
                if (event.getLink() != null) {
                    existingEvent.setLink(event.getLink());
                }
                if (event.getPhone() != null) {
                    existingEvent.setPhone(event.getPhone());
                }
                if (event.getEmail() != null) {
                    existingEvent.setEmail(event.getEmail());
                }
                if (event.getDescription() != null) {
                    existingEvent.setDescription(event.getDescription());
                }

                return existingEvent;
            })
            .map(eventRepository::save);
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Event> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable);
    }

    /**
     * Get one event by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Event> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }
}
