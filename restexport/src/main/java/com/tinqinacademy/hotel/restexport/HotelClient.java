package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.HotelFeignClientApiPaths;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.time.LocalDate;

@Headers({
        "Content-Type: application/json"
})
public interface HotelClient {
    @RequestLine(HotelFeignClientApiPaths.CHECK_ROOMS)
    CheckRoomsOutput checkRooms(@Param LocalDate startDate,
                                @Param LocalDate endDate,
                                @Param String bedSize,
                                @Param String bathroomType,
                                @Param Integer bedCount);

    @RequestLine(HotelFeignClientApiPaths.GET_ROOM_BY_ID)
    GetRoomByIdOutput getRoomById(@Param String roomId);

    @RequestLine(HotelFeignClientApiPaths.BOOK_ROOM)
    BookRoomOutput bookRoom(@Param String roomId,
                            BookRoomInput input);

    @RequestLine(HotelFeignClientApiPaths.UNBOOK_ROOM)
    UnbookRoomOutput unbookRoom(@Param String bookingId,
                                UnbookRoomInput input);

    @RequestLine(HotelFeignClientApiPaths.REGISTER_VISITOR)
    RegisterVisitorOutput registerVisitor(@Param String bookingId,
                                          RegisterVisitorInput input);

    @RequestLine(HotelFeignClientApiPaths.GET_REGISTERED_VISITORS)
    GetRegisteredVisitorsOutput getRegisteredVisitors(@Param String roomNo,
                                                      @Param String firstName,
                                                      @Param String lastName,
                                                      @Param String phoneNo,
                                                      @Param String civilNumber,
                                                      @Param LocalDate birthday,
                                                      @Param String idIssueAuthority,
                                                      @Param LocalDate idIssueDate);

    @RequestLine(HotelFeignClientApiPaths.CREATE_ROOM)
    CreateRoomOutput createRoom(CreateRoomInput input);

    @RequestLine(HotelFeignClientApiPaths.UPDATE_ROOM)
    UpdateRoomOutput updateRoom(@Param String roomId,
                                UpdateRoomInput input);

    @RequestLine(HotelFeignClientApiPaths.PARTIAL_UPDATE_ROOM)
    PartialUpdateRoomOutput partialUpdateRoom(@Param String roomId,
                                              PartialUpdateRoomInput input);

    @RequestLine(HotelFeignClientApiPaths.DELETE_ROOM)
    DeleteRoomOutput deleteRoom(@Param String roomId);
}
