package com.tinqinacademy.hotel.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.contracts.base.OperationOutput;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoom;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomAvailability;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomInfo;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Tag(name = "Hotel REST APIs")
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final CheckRoomAvailability checkRoomAvailabilityOperation;
    private final GetRoomInfo getRoomInfoOperation;
    private final UnbookRoom unbookRoomOperation;
    private final BookRoom bookRoomOperation;
    private final ObjectMapper objectMapper;

    @GetMapping(RestApiPaths.CHECK_ROOM)
    @Operation(
            summary = "Check room REST API",
            description = "Checks whether a room is available for a certain period."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<?> checkRoomAvailability(@RequestParam(required = false) @Schema(example = "2021-05-22") LocalDate startDate,
                                                   @RequestParam(required = false) @Schema(example = "2021-05-25") LocalDate endDate,
                                                   @RequestParam(required = false) @Schema(example = "kingSize") String bedSize,
                                                   @RequestParam(required = false) @Schema(example = "private") String bathroomType,
                                                   @RequestParam(required = false) @Schema(example = "2") Integer bedCount) {
        CheckRoomsInput input = CheckRoomsInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedSize(BedSize.getBedSize(bedSize))
                .bathroomType(BathroomType.getBathroomType(bathroomType))
                .bedCount(bedCount)
                .build();

        Either<ErrorOutput, CheckRoomsOutput> result = checkRoomAvailabilityOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }

    @GetMapping(RestApiPaths.GET_ROOM_INFO)
    @Operation(
            summary = "Info room REST API",
            description = "Returns basic info for a room with a specified id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<?> getRoomInfo(@PathVariable @Schema(example = "15") String roomId) {
        GetRoomByIdInput input = GetRoomByIdInput.builder()
                .roomId(roomId)
                .build();

        Either<ErrorOutput, GetRoomByIdOutput> result = getRoomInfoOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }

    @PostMapping(RestApiPaths.BOOK_ROOM)
    @Operation(
            summary = "Book room REST API",
            description = "Books the room specified."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<?> bookRoom(@PathVariable @Schema(example = "15") String roomId,
                                      @RequestBody BookRoomInput input) {
        input.setRoomId(roomId);

        Either<ErrorOutput, BookRoomOutput> result = bookRoomOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }

    @DeleteMapping(RestApiPaths.UNBOOK_ROOM)
    @Operation(
            summary = "Unbook room REST API",
            description = "Unbooks a room that the user has already been booked."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<?> unbookRoom(@PathVariable @Schema(example = "15") String bookingId) {
        UnbookRoomInput input = UnbookRoomInput.builder()
                .bookingId(bookingId)
                .build();

        Either<ErrorOutput, UnbookRoomOutput> result = unbookRoomOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }
}
