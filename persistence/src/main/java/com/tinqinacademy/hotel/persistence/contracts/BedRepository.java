package com.tinqinacademy.hotel.persistence.contracts;

import com.tinqinacademy.hotel.persistence.models.Bed;

import java.util.Optional;

public interface BedRepository extends GenericRepository<Bed> {
    void initializeBeds();
    Optional<Bed> getBedByBedSize(String bedSize);
}
