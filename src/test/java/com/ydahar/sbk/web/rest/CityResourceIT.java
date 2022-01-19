package com.ydahar.sbk.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ydahar.sbk.IntegrationTest;
import com.ydahar.sbk.domain.City;
import com.ydahar.sbk.repository.CityRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CityResourceIT {

    private static final String DEFAULT_DEPARTEMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_GPS_LAT = 1D;
    private static final Double UPDATED_GPS_LAT = 2D;

    private static final Double DEFAULT_GPS_LNG = 1D;
    private static final Double UPDATED_GPS_LNG = 2D;

    private static final String ENTITY_API_URL = "/api/cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityMockMvc;

    private City city;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity(EntityManager em) {
        City city = new City()
            .departementCode(DEFAULT_DEPARTEMENT_CODE)
            .zipCode(DEFAULT_ZIP_CODE)
            .name(DEFAULT_NAME)
            .gpsLat(DEFAULT_GPS_LAT)
            .gpsLng(DEFAULT_GPS_LNG);
        return city;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createUpdatedEntity(EntityManager em) {
        City city = new City()
            .departementCode(UPDATED_DEPARTEMENT_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .name(UPDATED_NAME)
            .gpsLat(UPDATED_GPS_LAT)
            .gpsLng(UPDATED_GPS_LNG);
        return city;
    }

    @BeforeEach
    public void initTest() {
        city = createEntity(em);
    }

    @Test
    @Transactional
    void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList
        restCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
            .andExpect(jsonPath("$.[*].departementCode").value(hasItem(DEFAULT_DEPARTEMENT_CODE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gpsLat").value(hasItem(DEFAULT_GPS_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].gpsLng").value(hasItem(DEFAULT_GPS_LNG.doubleValue())));
    }

    @Test
    @Transactional
    void getCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get the city
        restCityMockMvc
            .perform(get(ENTITY_API_URL_ID, city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId().intValue()))
            .andExpect(jsonPath("$.departementCode").value(DEFAULT_DEPARTEMENT_CODE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gpsLat").value(DEFAULT_GPS_LAT.doubleValue()))
            .andExpect(jsonPath("$.gpsLng").value(DEFAULT_GPS_LNG.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
