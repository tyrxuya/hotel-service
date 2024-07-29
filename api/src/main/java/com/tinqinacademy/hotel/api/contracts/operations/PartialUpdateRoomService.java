package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;

public interface PartialUpdateRoomService {
    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);
}
