package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;

public interface HotelService {
    CheckRoomsOutput checkRoomAvailability(CheckRoomsInput input);

    GetRoomByIdOutput getRoomInfo(GetRoomByIdInput input);

    BookRoomOutput bookRoom(BookRoomInput input);

    UnbookRoomOutput unbookRoom(UnbookRoomInput input);
}
