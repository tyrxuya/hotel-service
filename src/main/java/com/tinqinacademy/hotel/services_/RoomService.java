package com.tinqinacademy.hotel.services_;

import com.tinqinacademy.hotel.models.BookRoom;
import com.tinqinacademy.hotel.models.RoomInput;
import com.tinqinacademy.hotel.models.RoomOutput;
import com.tinqinacademy.hotel.models.Test;

public interface RoomService {
    String bookRoom(Test test);
    String checkRoom(Integer roomNumber);
    RoomOutput addRoom(RoomInput input);
    String deleteRoom(Integer roomNumber);
    String editRoom(Integer roomNumber);
    String reserveRoom(BookRoom bookRoom);
}
