package com.tinqinacademy.hotel.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.contracts.operations.BookRoomService;
import com.tinqinacademy.hotel.api.contracts.operations.CheckRoomAvailabilityService;
import com.tinqinacademy.hotel.api.contracts.operations.GetRoomInfoService;
import com.tinqinacademy.hotel.api.contracts.operations.UnbookRoomService;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@Tag(name = "Hotel REST APIs")
@RequiredArgsConstructor
public class HotelController {
    private final CheckRoomAvailabilityService checkRoomAvailabilityService;
    private final GetRoomInfoService getRoomInfoService;
    private final UnbookRoomService unbookRoomService;
    private final BookRoomService bookRoomService;
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
    public ResponseEntity<CheckRoomsOutput> checkRoomAvailability(@RequestParam(required = false) @Schema(example = "2021-05-22") LocalDate startDate,
                                                                  @RequestParam(required = false) @Schema(example = "2021-05-25") LocalDate endDate,
                                                                  @RequestParam(required = false) @Schema(example = "kingSize") String bedSize,
                                                                  @RequestParam(required = false) @Schema(example = "private") String bathroomType) {
        CheckRoomsInput input = CheckRoomsInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedSize(BedSize.getBedSize(bedSize))
                .bathroomType(BathroomType.getBathroomType(bathroomType))
                .build();

        CheckRoomsOutput result = checkRoomAvailabilityService.checkRoomAvailability(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<GetRoomByIdOutput> getRoomInfo(@PathVariable @Schema(example = "15") String roomId) {
        GetRoomByIdInput input = GetRoomByIdInput.builder()
                .roomId(UUID.fromString(roomId))
                .build();

        GetRoomByIdOutput result = getRoomInfoService.getRoomInfo(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<BookRoomOutput> bookRoom(@PathVariable @Schema(example = "15") String roomId,
                                                   @RequestBody @Valid BookRoomInput input) {
        input.setRoomId(UUID.fromString(roomId));

        BookRoomOutput result = bookRoomService.bookRoom(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<UnbookRoomOutput> unbookRoom(@PathVariable @Schema(example = "15") String bookingId) {
        UnbookRoomInput input = UnbookRoomInput.builder()
                .bookingId(UUID.fromString(bookingId))
                .build();

        UnbookRoomOutput result = unbookRoomService.unbookRoom(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
