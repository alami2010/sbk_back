package com.ydahar.sbk.repository;

import com.ydahar.sbk.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long> {


}


