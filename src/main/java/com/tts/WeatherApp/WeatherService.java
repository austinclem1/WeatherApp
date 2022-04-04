package com.tts.WeatherApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class WeatherService {
    @Value("${api_key}")
    private String apiKey;

    @Autowired
    private ZipCodeSearchRepository zipCodeSearchRepository;

    public Response getForecast(String zipCode) {
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" +
                zipCode + "&units=imperial&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
            Response response = restTemplate.getForObject(url, Response.class);
            ZipCodeSearch search = new ZipCodeSearch(Integer.parseInt(zipCode));
            if (zipCodeSearchRepository.count() < 10) {
                zipCodeSearchRepository.save(search);
            } else {
                List<ZipCodeSearch> existingZipCodes = new ArrayList<>();
                for( ZipCodeSearch zipCodeSearch : zipCodeSearchRepository.findAll()) {
                    existingZipCodes.add(zipCodeSearch);
                }
                Collections.sort(existingZipCodes, new Comparator<ZipCodeSearch>() {
                    @Override
                    public int compare(ZipCodeSearch zip1, ZipCodeSearch zip2) {
                        return zip1.getCreatedAt().compareTo(zip2.getCreatedAt());
                    }
                });
                ZipCodeSearch zipCodeSearchToDelete = existingZipCodes.get(0);
                zipCodeSearchRepository.deleteById(zipCodeSearchToDelete.getZipCode());
                zipCodeSearchRepository.save(search);
            }
            return response;
        } catch (HttpClientErrorException ex) {
            Response response = new Response();
            response.setName("error");
            return response;
        }
    }

    public List<Integer> getRecentZipcodes() {
        List<Integer> result = new ArrayList<>();
        for (ZipCodeSearch zipCodeSearch : zipCodeSearchRepository.findAll()) {
            result.add(zipCodeSearch.getZipCode());
        }
        Collections.sort(result);
        return result;
    }
}