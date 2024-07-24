package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.entities.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BedRepository extends JpaRepository<Bed, UUID> {
    Optional<Bed> findBedByBedSize(BedSize bedSize);
}
