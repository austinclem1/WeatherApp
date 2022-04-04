package com.tts.WeatherApp;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class ZipCodeSearch {
    @Id
    private Integer zipCode;
    @CreatedDate
    private Instant createdDate;

    public ZipCodeSearch() {

    }

    public ZipCodeSearch(Integer zipCode) {
        setZipCode(zipCode);
        setCreatedAt(Instant.now());
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Instant getCreatedAt() {
        return createdDate;
    }

    public void setCreatedAt(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ZipCodeSearch{" +
                "zipCode=" + zipCode +
                ", createdDate=" + createdDate +
                '}';
    }
}
