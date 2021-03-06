package com.ydahar.sbk.web.rest;

import com.ydahar.sbk.domain.City;
import com.ydahar.sbk.repository.CityRepository;
import com.ydahar.sbk.service.CityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link City}.
 */
@RestController
@RequestMapping("/")
public class HelloResource {

    private final Logger log = LoggerFactory.getLogger(HelloResource.class);

    @GetMapping("/")
    public Map<String, String> hello() {
        log.debug("REST request to hello world");
        Map<String, String> map = new HashMap<>();
        map.put("health","OK");
        map.put("app","SBK Go dance");
        return map;
    }
}
