package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.models.BookRoom;
import com.tinqinacademy.hotel.api.models.RoomInput;
import com.tinqinacademy.hotel.api.models.RoomOutput;
import com.tinqinacademy.hotel.api.models.Test;

public interface RoomService {
    String bookRoom(Test test);

    String checkRoom(Integer roomNumber);

    RoomOutput addRoom(RoomInput input);

    String deleteRoom(Integer roomNumber);

    String editRoom(Integer roomNumber);

    String reserveRoom(BookRoom bookRoom);
}
