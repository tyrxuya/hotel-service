package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;

public interface UpdateRoomService {
    UpdateRoomOutput updateRoom(UpdateRoomInput input);
}
