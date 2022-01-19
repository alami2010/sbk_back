package com.ydahar.sbk.service;

import com.ydahar.sbk.domain.City;
import com.ydahar.sbk.repository.CityRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link City}.
 */
@Service
@Transactional
public class CityService {

    private final Logger log = LoggerFactory.getLogger(CityService.class);

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Save a city.
     *
     * @param city the entity to save.
     * @return the persisted entity.
     */
    public City save(City city) {
        log.debug("Request to save City : {}", city);
        return cityRepository.save(city);
    }

    /**
     * Partially update a city.
     *
     * @param city the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<City> partialUpdate(City city) {
        log.debug("Request to partially update City : {}", city);

        return cityRepository
            .findById(city.getId())
            .map(existingCity -> {
                if (city.getDepartementCode() != null) {
                    existingCity.setDepartementCode(city.getDepartementCode());
                }
                if (city.getZipCode() != null) {
                    existingCity.setZipCode(city.getZipCode());
                }
                if (city.getName() != null) {
                    existingCity.setName(city.getName());
                }
                if (city.getGpsLat() != null) {
                    existingCity.setGpsLat(city.getGpsLat());
                }
                if (city.getGpsLng() != null) {
                    existingCity.setGpsLng(city.getGpsLng());
                }

                return existingCity;
            })
            .map(cityRepository::save);
    }

    /**
     * Get all the cities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<City> findAll() {
        log.debug("Request to get all Cities");
        return cityRepository.findAll();
    }

    /**
     * Get one city by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<City> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id);
    }

    /**
     * Delete the city by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
    }
}
