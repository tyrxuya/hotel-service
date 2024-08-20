package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.HotelRestApiPaths;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoom;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoom;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetVisitorsInfo;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.VisitorInfo;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoom;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitor;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoom;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Tag(name = "System REST APIs")
@RequiredArgsConstructor
public class SystemController extends BaseController {
    private final RegisterVisitor registerVisitorOperation;
    private final GetVisitorsInfo getVisitorsInfoOperation;
    private final CreateRoom createRoomOperation;
    private final UpdateRoom updateRoomOperation;
    private final PartialUpdateRoom partialUpdateRoomOperation;
    private final DeleteRoom deleteRoomOperation;

    @PostMapping(HotelRestApiPaths.REGISTER_VISITOR)
    @Operation(
            summary = "Register visitor REST API",
            description = "Registers a visitor as room renter."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visitor registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<?> registerVisitor(@PathVariable String bookingId,
                                             @RequestBody RegisterVisitorInput input) {
        input.setBookingId(bookingId);

        Either<ErrorOutput, RegisterVisitorOutput> result = registerVisitorOperation.process(input);
        return getOutput(result, HttpStatus.CREATED);
    }

    @GetMapping(HotelRestApiPaths.GET_VISITORS_INFO)
    @Operation(
            summary = "Info visitor REST API",
            description = "Provides a report based on various criteria. Provides info when room was occupied and by whom. Can report when a user has occupied rooms."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitors found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Visitors not found")
    })
    public ResponseEntity<?> getVisitorsInfo(@RequestParam(required = false) @Schema(example = "101b") String roomNo,
                                             @RequestParam(required = false) @Schema(example = "vanio") String firstName,
                                             @RequestParam(required = false) @Schema(example = "georgiev") String lastName,
                                             @RequestParam(required = false) @Schema(example = "+359887839281") String phoneNo,
                                             @RequestParam(required = false) @Schema(example = "0348888888") String civilNumber,
                                             @RequestParam(required = false) @Schema(example = "2003-09-22") LocalDate birthday,
                                             @RequestParam(required = false) @Schema(example = "mvr varna") String idIssueAuthority,
                                             @RequestParam(required = false) @Schema(example = "2015-05-22") LocalDate idIssueDate) {
        VisitorInfo visitor = VisitorInfo.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .civilNumber(civilNumber)
                .idIssueAuthority(idIssueAuthority)
                .idIssueDate(idIssueDate)
                .birthday(birthday)
                .build();

        GetRegisteredVisitorsInput input = GetRegisteredVisitorsInput.builder()
                .roomNo(roomNo)
                .visitor(visitor)
                .build();

        Either<ErrorOutput, GetRegisteredVisitorsOutput> result = getVisitorsInfoOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }

    @PostMapping(HotelRestApiPaths.CREATE_ROOM)
    @Operation(
            summary = "Create room REST API",
            description = "Admin creates a new room with the specified parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomInput input) {
        Either<ErrorOutput, CreateRoomOutput> result = createRoomOperation.process(input);
        return getOutput(result, HttpStatus.CREATED);
    }

    @PutMapping(HotelRestApiPaths.UPDATE_ROOM)
    @Operation(
            summary = "Update room REST API",
            description = "Admin updates the info regarding a certain room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room info updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<?> updateRoom(@PathVariable String roomId,
                                        @RequestBody UpdateRoomInput input) {
        input.setRoomId(roomId);

        Either<ErrorOutput, UpdateRoomOutput> result = updateRoomOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }

    @PatchMapping(HotelRestApiPaths.PARTIAL_UPDATE_ROOM)
    @Operation(
            summary = "Partially update room REST API",
            description = "Admin partial update of room data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room info updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<?> partialUpdateRoom(@PathVariable @Schema(example = "15") String roomId,
                                               @RequestBody PartialUpdateRoomInput input) {
        input.setRoomId(roomId);

        Either<ErrorOutput, PartialUpdateRoomOutput> result = partialUpdateRoomOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }

    @DeleteMapping(HotelRestApiPaths.DELETE_ROOM)
    @Operation(
            summary = "Delete room REST API",
            description = "Deletes a room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .roomId(roomId)
                .build();

        Either<ErrorOutput, DeleteRoomOutput> result = deleteRoomOperation.process(input);
        return getOutput(result, HttpStatus.OK);
    }
}
