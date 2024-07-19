package com.tinqinacademy.hotel.persistence.contracts;

import com.tinqinacademy.hotel.persistence.models.Room;

public interface RoomRepository extends GenericRepository<Room> {
    void update(Room room);
}
