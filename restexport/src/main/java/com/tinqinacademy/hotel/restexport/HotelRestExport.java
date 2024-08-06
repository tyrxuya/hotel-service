package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
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
public interface HotelRestExport {
    @RequestLine("GET /api/v1/hotel/rooms?startDate={startDate}&endDate={endDate}&bedSize={bedSize}&bathroomType={bathroomType}&bedCount={bedCount}")
    CheckRoomsOutput checkRooms(@Param LocalDate startDate,
                                @Param LocalDate endDate,
                                @Param String bedSize,
                                @Param String bathroomType,
                                @Param Integer bedCount);

    @RequestLine("GET /api/v1/hotel/{roomId}")
    GetRoomByIdOutput getRoomById(@Param String roomId);

    @RequestLine("POST /api/v1/hotel/{roomId}")
    BookRoomOutput createBookRoom(@Param String roomId,
                                  BookRoomInput input);

    @RequestLine("DELETE /api/v1/hotel/{bookingId}")
    UnbookRoomOutput unbookRoom(@Param String bookingId);

    @RequestLine("POST /api/vi/system/register/{bookingId}")
    RegisterVisitorOutput registerVisitor(@Param String bookingId,
                                          RegisterVisitorInput input);

    @RequestLine("GET /api/vi/system/register?roomNo={roomNo}&firstName={firstName}&lastName={lastName}&phoneNo={phoneNo}&civilNumber={civilNumber}&birthday={birthday}&idIssueAuthority={idIssueAuthority}&idIssueDate={idIssueDate}")
    GetRegisteredVisitorsOutput getRegisteredVisitors(@Param String roomNo,
                                                      @Param String firstName,
                                                      @Param String lastName,
                                                      @Param String phoneNo,
                                                      @Param String civilNumber,
                                                      @Param LocalDate birthday,
                                                      @Param String idIssueAuthority,
                                                      @Param LocalDate idIssueDate);

    @RequestLine("POST /api/vi/system/room")
    CreateRoomOutput createRoom(CreateRoomInput input);

    @RequestLine("PUT /api/vi/system/room/{roomId}")
    UpdateRoomOutput updateRoom(@Param String roomId,
                                UpdateRoomInput input);

    @RequestLine("PATCH /api/vi/system/room/{roomId}")
    PartialUpdateRoomOutput partialUpdateRoom(@Param String roomId,
                                              PartialUpdateRoomInput input);

    @RequestLine("DELETE /api/vi/system/room/{roomId}")
    DeleteRoomOutput deleteRoom(@Param String roomId);

    @RequestLine("POST /api/v1/user")
    CreateUserOutput createUser(CreateUserInput input);

    @RequestLine("DELETE /api/v1/user/{userId}")
    DeleteUserOutput deleteUser(@Param String userId);

    @RequestLine("GET /api/v1/user/{userId}")
    GetUserOutput getUser(@Param String userId);
}
