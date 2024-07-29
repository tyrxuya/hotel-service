package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;

public interface CheckRoomAvailabilityService {
    CheckRoomsOutput checkRoomAvailability(CheckRoomsInput input);
}
