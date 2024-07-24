package com.tinqinacademy.hotel.persistence.initializers;

import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class BedInitializer implements ApplicationRunner {
    private final BedRepository bedRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (BedSize bedSize : BedSize.values()) {
            Bed bed = bedRepository.findBedByBedSize(bedSize)
                    .orElse(null);
            if (Objects.isNull(bed)) {
                bed = Bed.builder()
                        .bedSize(bedSize)
                        .capacity(bedSize.getCapacity())
                        .build();
                bedRepository.save(bed);
            }
        }
    }
}
