package com.ydahar.sbk.web.rest;

import com.ydahar.sbk.domain.City;
import com.ydahar.sbk.domain.Dance;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link City}.
 */
@RestController
@RequestMapping("/api")
public class DanceResource {

    private final Logger log = LoggerFactory.getLogger(DanceResource.class);


    /**
     * {@code GET  /dances} : get all the dances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dances in
     * body.
     */
    @GetMapping("/dances")
    public List<Dance> getAllCities() {
        log.debug("REST request to get all Cities");
        return Stream.of(Dance.values()).collect(Collectors.toList());
    }
}
