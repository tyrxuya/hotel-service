package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;

public interface UnbookRoomService {
    UnbookRoomOutput unbookRoom(UnbookRoomInput input);
}
