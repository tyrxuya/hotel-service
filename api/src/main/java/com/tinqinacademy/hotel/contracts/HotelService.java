package com.tinqinacademy.hotel.contracts;

import com.tinqinacademy.hotel.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomOutput;

public interface HotelService {
    CheckRoomsOutput checkRoomAvailability(CheckRoomsInput input);

    GetRoomByIdOutput getRoomInfo(GetRoomByIdInput input);

    BookRoomOutput bookRoom(BookRoomInput input);

    UnbookRoomOutput unbookRoom(UnbookRoomInput input);
}
