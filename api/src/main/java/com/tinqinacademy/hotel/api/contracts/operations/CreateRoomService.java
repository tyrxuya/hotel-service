package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;

public interface CreateRoomService {
    CreateRoomOutput createRoom(CreateRoomInput input);
}
