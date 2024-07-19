package com.tinqinacademy.hotel.rest;

import com.tinqinacademy.hotel.persistence.contracts.BedRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BedLoader implements ApplicationRunner {
    private final BedRepository bedRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        bedRepository.initializeBeds();
    }
}
