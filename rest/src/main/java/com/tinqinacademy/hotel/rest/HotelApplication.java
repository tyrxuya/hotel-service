package com.tinqinacademy.hotel.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tinqinacademy.hotel"})
@EnableJpaRepositories(basePackages = {"com.tinqinacademy.hotel.persistence.repositories"})
@EntityScan(basePackages = {"com.tinqinacademy.hotel.persistence.entities"})
public class HotelApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }
}
