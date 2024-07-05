package com.tinqinacademy.hotel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.models.BathroomType;
import com.tinqinacademy.hotel.models.BedSize;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.services_.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Tag(name = "Hotel REST APIs")
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final ObjectMapper objectMapper;

    @GetMapping("/rooms")
    @Operation(
            summary = "Check room REST API",
            description = "Checks whether a room is available for a certain period."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<CheckRoomsOutput> checkRoomAvailability(@RequestParam(required = false, value = "startDate") @Schema(example = "2021-05-22") LocalDate startDate,
                                                                  @RequestParam(required = false, value = "endDate") @Schema(example = "2021-05-25") LocalDate endDate,
                                                                  @RequestParam(required = false, value = "bedCount") @Schema(example = "2") Integer bedCount,
                                                                  @RequestParam(required = false, value = "bedSize") @Schema(example = "kingSize") String bedSize,
                                                                  @RequestParam(required = false, value = "bathroomType") @Schema(example = "private") String bathroomType) {
        CheckRoomsInput input = CheckRoomsInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedCount(bedCount)
                .bedSize(BedSize.getBedSize(bedSize))
                .bathroomType(BathroomType.getBathroomType(bathroomType))
                .build();
        return new ResponseEntity<>(hotelService.checkRoomAvailability(input), HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    @Operation(
            summary = "Info room REST API",
            description = "Returns basic info for a room with a specified id."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    public ResponseEntity<GetRoomByIdOutput> getRoomInfo(@PathVariable @Schema(example = "15") String roomId) {
        GetRoomByIdInput input = GetRoomByIdInput.builder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(hotelService.getRoomInfo(input), HttpStatus.OK);
    }

    @PostMapping("/{roomId}")
    @Operation(
            summary = "Book room REST API",
            description = "Books the room specified."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<BookRoomOutput> bookRoom(@PathVariable @Schema(example = "15") String roomId,
                                                   @RequestBody BookRoomInput input) {
        input = input.toBuilder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(hotelService.bookRoom(input), HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    @Operation(
            summary = "Unbook room REST API",
            description = "Unbooks a room that the user has already been booked."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<UnbookRoomOutput> unbookRoom(@PathVariable @Schema(example = "15") String bookingId) {
        UnbookRoomInput input = UnbookRoomInput.builder()
                .bookingId(bookingId)
                .build();

        return new ResponseEntity<>(hotelService.unbookRoom(input), HttpStatus.OK);
    }
}
