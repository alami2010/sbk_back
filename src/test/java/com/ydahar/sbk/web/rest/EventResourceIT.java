package com.ydahar.sbk.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ydahar.sbk.IntegrationTest;
import com.ydahar.sbk.domain.Event;
import com.ydahar.sbk.repository.EventRepository;
import com.ydahar.sbk.service.criteria.EventCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_START_HOUR = "AAAAAAAAAA";
    private static final String UPDATED_START_HOUR = "BBBBBBBBBB";

    private static final String DEFAULT_END_HOUR = "AAAAAAAAAA";
    private static final String UPDATED_END_HOUR = "BBBBBBBBBB";

    private static final String DEFAULT_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_LIVE = false;
    private static final Boolean UPDATED_IS_LIVE = true;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_LAT = "AAAAAAAAAA";
    private static final String UPDATED_ADD_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_LONG = "AAAAAAAAAA";
    private static final String UPDATED_ADD_LONG = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventMockMvc;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .startHour(DEFAULT_START_HOUR)
            .endHour(DEFAULT_END_HOUR)
            .period(DEFAULT_PERIOD)
            .price(DEFAULT_PRICE)
            .type(DEFAULT_TYPE)
            .isLive(DEFAULT_IS_LIVE)
            .country(DEFAULT_COUNTRY)
            .address(DEFAULT_ADDRESS)
            .addLat(DEFAULT_ADD_LAT)
            .addLong(DEFAULT_ADD_LONG)
            .image(DEFAULT_IMAGE)
            .link(DEFAULT_LINK)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .description(DEFAULT_DESCRIPTION);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .period(UPDATED_PERIOD)
            .price(UPDATED_PRICE)
            .type(UPDATED_TYPE)
            .isLive(UPDATED_IS_LIVE)
            .country(UPDATED_COUNTRY)
            .address(UPDATED_ADDRESS)
            .addLat(UPDATED_ADD_LAT)
            .addLong(UPDATED_ADD_LONG)
            .image(UPDATED_IMAGE)
            .link(UPDATED_LINK)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .description(UPDATED_DESCRIPTION);
        return event;
    }

    @BeforeEach
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        // Create the Event
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEvent.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testEvent.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testEvent.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testEvent.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getIsLive()).isEqualTo(DEFAULT_IS_LIVE);
        assertThat(testEvent.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEvent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEvent.getAddLat()).isEqualTo(DEFAULT_ADD_LAT);
        assertThat(testEvent.getAddLong()).isEqualTo(DEFAULT_ADD_LONG);
        assertThat(testEvent.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testEvent.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testEvent.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testEvent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createEventWithExistingId() throws Exception {
        // Create the Event with an existing ID
        event.setId(1L);

        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setTitle(null);

        // Create the Event, which fails.

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR)))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isLive").value(hasItem(DEFAULT_IS_LIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].addLat").value(hasItem(DEFAULT_ADD_LAT)))
            .andExpect(jsonPath("$.[*].addLong").value(hasItem(DEFAULT_ADD_LONG)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc
            .perform(get(ENTITY_API_URL_ID, event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.startHour").value(DEFAULT_START_HOUR))
            .andExpect(jsonPath("$.endHour").value(DEFAULT_END_HOUR))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.isLive").value(DEFAULT_IS_LIVE.booleanValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.addLat").value(DEFAULT_ADD_LAT))
            .andExpect(jsonPath("$.addLong").value(DEFAULT_ADD_LONG))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getEventsByIdFiltering() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        Long id = event.getId();

        defaultEventShouldBeFound("id.equals=" + id);
        defaultEventShouldNotBeFound("id.notEquals=" + id);

        defaultEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventShouldNotBeFound("id.greaterThan=" + id);

        defaultEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title equals to DEFAULT_TITLE
        defaultEventShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the eventList where title equals to UPDATED_TITLE
        defaultEventShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title not equals to DEFAULT_TITLE
        defaultEventShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the eventList where title not equals to UPDATED_TITLE
        defaultEventShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultEventShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the eventList where title equals to UPDATED_TITLE
        defaultEventShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title is not null
        defaultEventShouldBeFound("title.specified=true");

        // Get all the eventList where title is null
        defaultEventShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByTitleContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title contains DEFAULT_TITLE
        defaultEventShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the eventList where title contains UPDATED_TITLE
        defaultEventShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title does not contain DEFAULT_TITLE
        defaultEventShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the eventList where title does not contain UPDATED_TITLE
        defaultEventShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date equals to DEFAULT_DATE
        defaultEventShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the eventList where date equals to UPDATED_DATE
        defaultEventShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date not equals to DEFAULT_DATE
        defaultEventShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the eventList where date not equals to UPDATED_DATE
        defaultEventShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEventShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the eventList where date equals to UPDATED_DATE
        defaultEventShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is not null
        defaultEventShouldBeFound("date.specified=true");

        // Get all the eventList where date is null
        defaultEventShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is greater than or equal to DEFAULT_DATE
        defaultEventShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the eventList where date is greater than or equal to UPDATED_DATE
        defaultEventShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is less than or equal to DEFAULT_DATE
        defaultEventShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the eventList where date is less than or equal to SMALLER_DATE
        defaultEventShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is less than DEFAULT_DATE
        defaultEventShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the eventList where date is less than UPDATED_DATE
        defaultEventShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is greater than DEFAULT_DATE
        defaultEventShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the eventList where date is greater than SMALLER_DATE
        defaultEventShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByStartHourIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where startHour equals to DEFAULT_START_HOUR
        defaultEventShouldBeFound("startHour.equals=" + DEFAULT_START_HOUR);

        // Get all the eventList where startHour equals to UPDATED_START_HOUR
        defaultEventShouldNotBeFound("startHour.equals=" + UPDATED_START_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByStartHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where startHour not equals to DEFAULT_START_HOUR
        defaultEventShouldNotBeFound("startHour.notEquals=" + DEFAULT_START_HOUR);

        // Get all the eventList where startHour not equals to UPDATED_START_HOUR
        defaultEventShouldBeFound("startHour.notEquals=" + UPDATED_START_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByStartHourIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where startHour in DEFAULT_START_HOUR or UPDATED_START_HOUR
        defaultEventShouldBeFound("startHour.in=" + DEFAULT_START_HOUR + "," + UPDATED_START_HOUR);

        // Get all the eventList where startHour equals to UPDATED_START_HOUR
        defaultEventShouldNotBeFound("startHour.in=" + UPDATED_START_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByStartHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where startHour is not null
        defaultEventShouldBeFound("startHour.specified=true");

        // Get all the eventList where startHour is null
        defaultEventShouldNotBeFound("startHour.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByStartHourContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where startHour contains DEFAULT_START_HOUR
        defaultEventShouldBeFound("startHour.contains=" + DEFAULT_START_HOUR);

        // Get all the eventList where startHour contains UPDATED_START_HOUR
        defaultEventShouldNotBeFound("startHour.contains=" + UPDATED_START_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByStartHourNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where startHour does not contain DEFAULT_START_HOUR
        defaultEventShouldNotBeFound("startHour.doesNotContain=" + DEFAULT_START_HOUR);

        // Get all the eventList where startHour does not contain UPDATED_START_HOUR
        defaultEventShouldBeFound("startHour.doesNotContain=" + UPDATED_START_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByEndHourIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where endHour equals to DEFAULT_END_HOUR
        defaultEventShouldBeFound("endHour.equals=" + DEFAULT_END_HOUR);

        // Get all the eventList where endHour equals to UPDATED_END_HOUR
        defaultEventShouldNotBeFound("endHour.equals=" + UPDATED_END_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByEndHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where endHour not equals to DEFAULT_END_HOUR
        defaultEventShouldNotBeFound("endHour.notEquals=" + DEFAULT_END_HOUR);

        // Get all the eventList where endHour not equals to UPDATED_END_HOUR
        defaultEventShouldBeFound("endHour.notEquals=" + UPDATED_END_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByEndHourIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where endHour in DEFAULT_END_HOUR or UPDATED_END_HOUR
        defaultEventShouldBeFound("endHour.in=" + DEFAULT_END_HOUR + "," + UPDATED_END_HOUR);

        // Get all the eventList where endHour equals to UPDATED_END_HOUR
        defaultEventShouldNotBeFound("endHour.in=" + UPDATED_END_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByEndHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where endHour is not null
        defaultEventShouldBeFound("endHour.specified=true");

        // Get all the eventList where endHour is null
        defaultEventShouldNotBeFound("endHour.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEndHourContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where endHour contains DEFAULT_END_HOUR
        defaultEventShouldBeFound("endHour.contains=" + DEFAULT_END_HOUR);

        // Get all the eventList where endHour contains UPDATED_END_HOUR
        defaultEventShouldNotBeFound("endHour.contains=" + UPDATED_END_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByEndHourNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where endHour does not contain DEFAULT_END_HOUR
        defaultEventShouldNotBeFound("endHour.doesNotContain=" + DEFAULT_END_HOUR);

        // Get all the eventList where endHour does not contain UPDATED_END_HOUR
        defaultEventShouldBeFound("endHour.doesNotContain=" + UPDATED_END_HOUR);
    }

    @Test
    @Transactional
    void getAllEventsByPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where period equals to DEFAULT_PERIOD
        defaultEventShouldBeFound("period.equals=" + DEFAULT_PERIOD);

        // Get all the eventList where period equals to UPDATED_PERIOD
        defaultEventShouldNotBeFound("period.equals=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEventsByPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where period not equals to DEFAULT_PERIOD
        defaultEventShouldNotBeFound("period.notEquals=" + DEFAULT_PERIOD);

        // Get all the eventList where period not equals to UPDATED_PERIOD
        defaultEventShouldBeFound("period.notEquals=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEventsByPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where period in DEFAULT_PERIOD or UPDATED_PERIOD
        defaultEventShouldBeFound("period.in=" + DEFAULT_PERIOD + "," + UPDATED_PERIOD);

        // Get all the eventList where period equals to UPDATED_PERIOD
        defaultEventShouldNotBeFound("period.in=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEventsByPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where period is not null
        defaultEventShouldBeFound("period.specified=true");

        // Get all the eventList where period is null
        defaultEventShouldNotBeFound("period.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByPeriodContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where period contains DEFAULT_PERIOD
        defaultEventShouldBeFound("period.contains=" + DEFAULT_PERIOD);

        // Get all the eventList where period contains UPDATED_PERIOD
        defaultEventShouldNotBeFound("period.contains=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEventsByPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where period does not contain DEFAULT_PERIOD
        defaultEventShouldNotBeFound("period.doesNotContain=" + DEFAULT_PERIOD);

        // Get all the eventList where period does not contain UPDATED_PERIOD
        defaultEventShouldBeFound("period.doesNotContain=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price equals to DEFAULT_PRICE
        defaultEventShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the eventList where price equals to UPDATED_PRICE
        defaultEventShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price not equals to DEFAULT_PRICE
        defaultEventShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the eventList where price not equals to UPDATED_PRICE
        defaultEventShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultEventShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the eventList where price equals to UPDATED_PRICE
        defaultEventShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price is not null
        defaultEventShouldBeFound("price.specified=true");

        // Get all the eventList where price is null
        defaultEventShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price is greater than or equal to DEFAULT_PRICE
        defaultEventShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the eventList where price is greater than or equal to UPDATED_PRICE
        defaultEventShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price is less than or equal to DEFAULT_PRICE
        defaultEventShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the eventList where price is less than or equal to SMALLER_PRICE
        defaultEventShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price is less than DEFAULT_PRICE
        defaultEventShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the eventList where price is less than UPDATED_PRICE
        defaultEventShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEventsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where price is greater than DEFAULT_PRICE
        defaultEventShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the eventList where price is greater than SMALLER_PRICE
        defaultEventShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where type equals to DEFAULT_TYPE
        defaultEventShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the eventList where type equals to UPDATED_TYPE
        defaultEventShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where type not equals to DEFAULT_TYPE
        defaultEventShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the eventList where type not equals to UPDATED_TYPE
        defaultEventShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEventShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the eventList where type equals to UPDATED_TYPE
        defaultEventShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where type is not null
        defaultEventShouldBeFound("type.specified=true");

        // Get all the eventList where type is null
        defaultEventShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByTypeContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where type contains DEFAULT_TYPE
        defaultEventShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the eventList where type contains UPDATED_TYPE
        defaultEventShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where type does not contain DEFAULT_TYPE
        defaultEventShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the eventList where type does not contain UPDATED_TYPE
        defaultEventShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEventsByIsLiveIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where isLive equals to DEFAULT_IS_LIVE
        defaultEventShouldBeFound("isLive.equals=" + DEFAULT_IS_LIVE);

        // Get all the eventList where isLive equals to UPDATED_IS_LIVE
        defaultEventShouldNotBeFound("isLive.equals=" + UPDATED_IS_LIVE);
    }

    @Test
    @Transactional
    void getAllEventsByIsLiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where isLive not equals to DEFAULT_IS_LIVE
        defaultEventShouldNotBeFound("isLive.notEquals=" + DEFAULT_IS_LIVE);

        // Get all the eventList where isLive not equals to UPDATED_IS_LIVE
        defaultEventShouldBeFound("isLive.notEquals=" + UPDATED_IS_LIVE);
    }

    @Test
    @Transactional
    void getAllEventsByIsLiveIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where isLive in DEFAULT_IS_LIVE or UPDATED_IS_LIVE
        defaultEventShouldBeFound("isLive.in=" + DEFAULT_IS_LIVE + "," + UPDATED_IS_LIVE);

        // Get all the eventList where isLive equals to UPDATED_IS_LIVE
        defaultEventShouldNotBeFound("isLive.in=" + UPDATED_IS_LIVE);
    }

    @Test
    @Transactional
    void getAllEventsByIsLiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where isLive is not null
        defaultEventShouldBeFound("isLive.specified=true");

        // Get all the eventList where isLive is null
        defaultEventShouldNotBeFound("isLive.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where country equals to DEFAULT_COUNTRY
        defaultEventShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the eventList where country equals to UPDATED_COUNTRY
        defaultEventShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEventsByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where country not equals to DEFAULT_COUNTRY
        defaultEventShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the eventList where country not equals to UPDATED_COUNTRY
        defaultEventShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEventsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultEventShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the eventList where country equals to UPDATED_COUNTRY
        defaultEventShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEventsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where country is not null
        defaultEventShouldBeFound("country.specified=true");

        // Get all the eventList where country is null
        defaultEventShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByCountryContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where country contains DEFAULT_COUNTRY
        defaultEventShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the eventList where country contains UPDATED_COUNTRY
        defaultEventShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEventsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where country does not contain DEFAULT_COUNTRY
        defaultEventShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the eventList where country does not contain UPDATED_COUNTRY
        defaultEventShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllEventsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where address equals to DEFAULT_ADDRESS
        defaultEventShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the eventList where address equals to UPDATED_ADDRESS
        defaultEventShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEventsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where address not equals to DEFAULT_ADDRESS
        defaultEventShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the eventList where address not equals to UPDATED_ADDRESS
        defaultEventShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEventsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultEventShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the eventList where address equals to UPDATED_ADDRESS
        defaultEventShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEventsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where address is not null
        defaultEventShouldBeFound("address.specified=true");

        // Get all the eventList where address is null
        defaultEventShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByAddressContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where address contains DEFAULT_ADDRESS
        defaultEventShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the eventList where address contains UPDATED_ADDRESS
        defaultEventShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEventsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where address does not contain DEFAULT_ADDRESS
        defaultEventShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the eventList where address does not contain UPDATED_ADDRESS
        defaultEventShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEventsByAddLatIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLat equals to DEFAULT_ADD_LAT
        defaultEventShouldBeFound("addLat.equals=" + DEFAULT_ADD_LAT);

        // Get all the eventList where addLat equals to UPDATED_ADD_LAT
        defaultEventShouldNotBeFound("addLat.equals=" + UPDATED_ADD_LAT);
    }

    @Test
    @Transactional
    void getAllEventsByAddLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLat not equals to DEFAULT_ADD_LAT
        defaultEventShouldNotBeFound("addLat.notEquals=" + DEFAULT_ADD_LAT);

        // Get all the eventList where addLat not equals to UPDATED_ADD_LAT
        defaultEventShouldBeFound("addLat.notEquals=" + UPDATED_ADD_LAT);
    }

    @Test
    @Transactional
    void getAllEventsByAddLatIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLat in DEFAULT_ADD_LAT or UPDATED_ADD_LAT
        defaultEventShouldBeFound("addLat.in=" + DEFAULT_ADD_LAT + "," + UPDATED_ADD_LAT);

        // Get all the eventList where addLat equals to UPDATED_ADD_LAT
        defaultEventShouldNotBeFound("addLat.in=" + UPDATED_ADD_LAT);
    }

    @Test
    @Transactional
    void getAllEventsByAddLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLat is not null
        defaultEventShouldBeFound("addLat.specified=true");

        // Get all the eventList where addLat is null
        defaultEventShouldNotBeFound("addLat.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByAddLatContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLat contains DEFAULT_ADD_LAT
        defaultEventShouldBeFound("addLat.contains=" + DEFAULT_ADD_LAT);

        // Get all the eventList where addLat contains UPDATED_ADD_LAT
        defaultEventShouldNotBeFound("addLat.contains=" + UPDATED_ADD_LAT);
    }

    @Test
    @Transactional
    void getAllEventsByAddLatNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLat does not contain DEFAULT_ADD_LAT
        defaultEventShouldNotBeFound("addLat.doesNotContain=" + DEFAULT_ADD_LAT);

        // Get all the eventList where addLat does not contain UPDATED_ADD_LAT
        defaultEventShouldBeFound("addLat.doesNotContain=" + UPDATED_ADD_LAT);
    }

    @Test
    @Transactional
    void getAllEventsByAddLongIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLong equals to DEFAULT_ADD_LONG
        defaultEventShouldBeFound("addLong.equals=" + DEFAULT_ADD_LONG);

        // Get all the eventList where addLong equals to UPDATED_ADD_LONG
        defaultEventShouldNotBeFound("addLong.equals=" + UPDATED_ADD_LONG);
    }

    @Test
    @Transactional
    void getAllEventsByAddLongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLong not equals to DEFAULT_ADD_LONG
        defaultEventShouldNotBeFound("addLong.notEquals=" + DEFAULT_ADD_LONG);

        // Get all the eventList where addLong not equals to UPDATED_ADD_LONG
        defaultEventShouldBeFound("addLong.notEquals=" + UPDATED_ADD_LONG);
    }

    @Test
    @Transactional
    void getAllEventsByAddLongIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLong in DEFAULT_ADD_LONG or UPDATED_ADD_LONG
        defaultEventShouldBeFound("addLong.in=" + DEFAULT_ADD_LONG + "," + UPDATED_ADD_LONG);

        // Get all the eventList where addLong equals to UPDATED_ADD_LONG
        defaultEventShouldNotBeFound("addLong.in=" + UPDATED_ADD_LONG);
    }

    @Test
    @Transactional
    void getAllEventsByAddLongIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLong is not null
        defaultEventShouldBeFound("addLong.specified=true");

        // Get all the eventList where addLong is null
        defaultEventShouldNotBeFound("addLong.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByAddLongContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLong contains DEFAULT_ADD_LONG
        defaultEventShouldBeFound("addLong.contains=" + DEFAULT_ADD_LONG);

        // Get all the eventList where addLong contains UPDATED_ADD_LONG
        defaultEventShouldNotBeFound("addLong.contains=" + UPDATED_ADD_LONG);
    }

    @Test
    @Transactional
    void getAllEventsByAddLongNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where addLong does not contain DEFAULT_ADD_LONG
        defaultEventShouldNotBeFound("addLong.doesNotContain=" + DEFAULT_ADD_LONG);

        // Get all the eventList where addLong does not contain UPDATED_ADD_LONG
        defaultEventShouldBeFound("addLong.doesNotContain=" + UPDATED_ADD_LONG);
    }

    @Test
    @Transactional
    void getAllEventsByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where image equals to DEFAULT_IMAGE
        defaultEventShouldBeFound("image.equals=" + DEFAULT_IMAGE);

        // Get all the eventList where image equals to UPDATED_IMAGE
        defaultEventShouldNotBeFound("image.equals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllEventsByImageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where image not equals to DEFAULT_IMAGE
        defaultEventShouldNotBeFound("image.notEquals=" + DEFAULT_IMAGE);

        // Get all the eventList where image not equals to UPDATED_IMAGE
        defaultEventShouldBeFound("image.notEquals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllEventsByImageIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where image in DEFAULT_IMAGE or UPDATED_IMAGE
        defaultEventShouldBeFound("image.in=" + DEFAULT_IMAGE + "," + UPDATED_IMAGE);

        // Get all the eventList where image equals to UPDATED_IMAGE
        defaultEventShouldNotBeFound("image.in=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllEventsByImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where image is not null
        defaultEventShouldBeFound("image.specified=true");

        // Get all the eventList where image is null
        defaultEventShouldNotBeFound("image.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByImageContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where image contains DEFAULT_IMAGE
        defaultEventShouldBeFound("image.contains=" + DEFAULT_IMAGE);

        // Get all the eventList where image contains UPDATED_IMAGE
        defaultEventShouldNotBeFound("image.contains=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllEventsByImageNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where image does not contain DEFAULT_IMAGE
        defaultEventShouldNotBeFound("image.doesNotContain=" + DEFAULT_IMAGE);

        // Get all the eventList where image does not contain UPDATED_IMAGE
        defaultEventShouldBeFound("image.doesNotContain=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    void getAllEventsByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where link equals to DEFAULT_LINK
        defaultEventShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the eventList where link equals to UPDATED_LINK
        defaultEventShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllEventsByLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where link not equals to DEFAULT_LINK
        defaultEventShouldNotBeFound("link.notEquals=" + DEFAULT_LINK);

        // Get all the eventList where link not equals to UPDATED_LINK
        defaultEventShouldBeFound("link.notEquals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllEventsByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where link in DEFAULT_LINK or UPDATED_LINK
        defaultEventShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the eventList where link equals to UPDATED_LINK
        defaultEventShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllEventsByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where link is not null
        defaultEventShouldBeFound("link.specified=true");

        // Get all the eventList where link is null
        defaultEventShouldNotBeFound("link.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByLinkContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where link contains DEFAULT_LINK
        defaultEventShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the eventList where link contains UPDATED_LINK
        defaultEventShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllEventsByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where link does not contain DEFAULT_LINK
        defaultEventShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the eventList where link does not contain UPDATED_LINK
        defaultEventShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllEventsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where phone equals to DEFAULT_PHONE
        defaultEventShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the eventList where phone equals to UPDATED_PHONE
        defaultEventShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllEventsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where phone not equals to DEFAULT_PHONE
        defaultEventShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the eventList where phone not equals to UPDATED_PHONE
        defaultEventShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllEventsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultEventShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the eventList where phone equals to UPDATED_PHONE
        defaultEventShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllEventsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where phone is not null
        defaultEventShouldBeFound("phone.specified=true");

        // Get all the eventList where phone is null
        defaultEventShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where phone contains DEFAULT_PHONE
        defaultEventShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the eventList where phone contains UPDATED_PHONE
        defaultEventShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllEventsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where phone does not contain DEFAULT_PHONE
        defaultEventShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the eventList where phone does not contain UPDATED_PHONE
        defaultEventShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllEventsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where email equals to DEFAULT_EMAIL
        defaultEventShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the eventList where email equals to UPDATED_EMAIL
        defaultEventShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEventsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where email not equals to DEFAULT_EMAIL
        defaultEventShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the eventList where email not equals to UPDATED_EMAIL
        defaultEventShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEventsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEventShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the eventList where email equals to UPDATED_EMAIL
        defaultEventShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEventsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where email is not null
        defaultEventShouldBeFound("email.specified=true");

        // Get all the eventList where email is null
        defaultEventShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByEmailContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where email contains DEFAULT_EMAIL
        defaultEventShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the eventList where email contains UPDATED_EMAIL
        defaultEventShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEventsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where email does not contain DEFAULT_EMAIL
        defaultEventShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the eventList where email does not contain UPDATED_EMAIL
        defaultEventShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description equals to DEFAULT_DESCRIPTION
        defaultEventShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the eventList where description equals to UPDATED_DESCRIPTION
        defaultEventShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description not equals to DEFAULT_DESCRIPTION
        defaultEventShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the eventList where description not equals to UPDATED_DESCRIPTION
        defaultEventShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEventShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the eventList where description equals to UPDATED_DESCRIPTION
        defaultEventShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description is not null
        defaultEventShouldBeFound("description.specified=true");

        // Get all the eventList where description is null
        defaultEventShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description contains DEFAULT_DESCRIPTION
        defaultEventShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the eventList where description contains UPDATED_DESCRIPTION
        defaultEventShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description does not contain DEFAULT_DESCRIPTION
        defaultEventShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the eventList where description does not contain UPDATED_DESCRIPTION
        defaultEventShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventShouldBeFound(String filter) throws Exception {
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR)))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isLive").value(hasItem(DEFAULT_IS_LIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].addLat").value(hasItem(DEFAULT_ADD_LAT)))
            .andExpect(jsonPath("$.[*].addLong").value(hasItem(DEFAULT_ADD_LONG)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventShouldNotBeFound(String filter) throws Exception {
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .period(UPDATED_PERIOD)
            .price(UPDATED_PRICE)
            .type(UPDATED_TYPE)
            .isLive(UPDATED_IS_LIVE)
            .country(UPDATED_COUNTRY)
            .address(UPDATED_ADDRESS)
            .addLat(UPDATED_ADD_LAT)
            .addLong(UPDATED_ADD_LONG)
            .image(UPDATED_IMAGE)
            .link(UPDATED_LINK)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .description(UPDATED_DESCRIPTION);

        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEvent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testEvent.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testEvent.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testEvent.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getIsLive()).isEqualTo(UPDATED_IS_LIVE);
        assertThat(testEvent.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEvent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEvent.getAddLat()).isEqualTo(UPDATED_ADD_LAT);
        assertThat(testEvent.getAddLong()).isEqualTo(UPDATED_ADD_LONG);
        assertThat(testEvent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testEvent.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testEvent.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEvent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, event.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .date(UPDATED_DATE)
            .isLive(UPDATED_IS_LIVE)
            .addLong(UPDATED_ADD_LONG)
            .image(UPDATED_IMAGE)
            .email(UPDATED_EMAIL)
            .description(UPDATED_DESCRIPTION);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testEvent.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testEvent.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testEvent.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getIsLive()).isEqualTo(UPDATED_IS_LIVE);
        assertThat(testEvent.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEvent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEvent.getAddLat()).isEqualTo(DEFAULT_ADD_LAT);
        assertThat(testEvent.getAddLong()).isEqualTo(UPDATED_ADD_LONG);
        assertThat(testEvent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testEvent.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testEvent.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testEvent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .period(UPDATED_PERIOD)
            .price(UPDATED_PRICE)
            .type(UPDATED_TYPE)
            .isLive(UPDATED_IS_LIVE)
            .country(UPDATED_COUNTRY)
            .address(UPDATED_ADDRESS)
            .addLat(UPDATED_ADD_LAT)
            .addLong(UPDATED_ADD_LONG)
            .image(UPDATED_IMAGE)
            .link(UPDATED_LINK)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .description(UPDATED_DESCRIPTION);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testEvent.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testEvent.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testEvent.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getIsLive()).isEqualTo(UPDATED_IS_LIVE);
        assertThat(testEvent.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEvent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEvent.getAddLat()).isEqualTo(UPDATED_ADD_LAT);
        assertThat(testEvent.getAddLong()).isEqualTo(UPDATED_ADD_LONG);
        assertThat(testEvent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testEvent.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testEvent.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEvent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, event.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, event.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
