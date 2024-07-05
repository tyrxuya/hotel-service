package com.tinqinacademy.hotel.services_;

import com.tinqinacademy.hotel.models.*;

public interface RoomService {
    String bookRoom(Test test);
    String checkRoom(Integer roomNumber);
    RoomOutput addRoom(RoomInput input);
    String deleteRoom(Integer roomNumber);
    String editRoom(Integer roomNumber);
    String reserveRoom(BookRoom bookRoom);
}
