package com.tts.WeatherApp;

import org.springframework.data.repository.CrudRepository;

import java.time.Instant;

public interface ZipCodeSearchRepository extends CrudRepository<ZipCodeSearch, Integer> {
}
