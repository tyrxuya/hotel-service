package com.tinqinacademy.hotel.persistence.contracts;

import com.tinqinacademy.hotel.persistence.models.Room;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends GenericRepository<Room> {
    void update(Room room);
    Optional<BigDecimal> getPriceById(UUID id);
}
