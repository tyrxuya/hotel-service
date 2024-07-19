package com.tinqinacademy.hotel.persistence.contracts;

import com.tinqinacademy.hotel.persistence.models.Guest;

import java.util.List;

public interface GuestRepository extends GenericRepository<Guest> {
    void saveAll(List<Guest> guests);
}
