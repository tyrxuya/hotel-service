package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;

public interface GetRoomInfoService {
    GetRoomByIdOutput getRoomInfo(GetRoomByIdInput input);
}
