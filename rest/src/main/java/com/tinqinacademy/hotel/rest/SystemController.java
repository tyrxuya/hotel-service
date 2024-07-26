package com.tinqinacademy.hotel.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.contracts.SystemService;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
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
@Tag(name = "System REST APIs")
@RequiredArgsConstructor
public class SystemController {
    private final SystemService systemService;
    private final ObjectMapper objectMapper;

    @PostMapping(RestApiPaths.REGISTER_VISITOR)
    @Operation(
            summary = "Register visitor REST API",
            description = "Registers a visitor as room renter."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<RegisterVisitorOutput> registerVisitor(@PathVariable UUID bookingId,
                                                                 @RequestBody @Valid RegisterVisitorInput input) {
        input.setBookingId(bookingId);

        RegisterVisitorOutput result = systemService.registerVisitor(input);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(RestApiPaths.GET_VISITORS_INFO)
    @Operation(
            summary = "Info visitor REST API",
            description = "Provides a report based on various criteria. Provides info when room was occupied and by whom. Can report when a user has occupied rooms."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<GetRegisteredVisitorsOutput> getVisitorsInfo(@RequestParam(required = false) @Schema(example = "101b") String roomNo,
                                                                       @RequestParam(required = false) @Schema(example = "vanio") String firstName,
                                                                       @RequestParam(required = false) @Schema(example = "georgiev") String lastName,
                                                                       @RequestParam(required = false) @Schema(example = "+359887839281") String phoneNo,
                                                                       @RequestParam(required = false) @Schema(example = "0348888888") String civilNumber,
                                                                       @RequestParam(required = false) @Schema(example = "2003-09-22") LocalDate birthday,
                                                                       @RequestParam(required = false) @Schema(example = "mvr varna") String idIssueAuthority,
                                                                       @RequestParam(required = false) @Schema(example = "2015-05-22") LocalDate idIssueDate) {
        HotelVisitorInput visitor = HotelVisitorInput.builder()
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

        GetRegisteredVisitorsOutput result = systemService.getVisitorsInfo(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(RestApiPaths.CREATE_ROOM)
    @Operation(
            summary = "Create room REST API",
            description = "Admin creates a new room with the specified parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<CreateRoomOutput> createRoom(@RequestBody @Valid CreateRoomInput input) {
        CreateRoomOutput result = systemService.createRoom(input);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(RestApiPaths.UPDATE_ROOM)
    @Operation(
            summary = "Update room REST API",
            description = "Admin updates the info regarding a certain room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<UpdateRoomOutput> updateRoom(@PathVariable UUID roomId,
                                                       @RequestBody @Valid UpdateRoomInput input) {
        input.setRoomId(roomId);

        UpdateRoomOutput result = systemService.updateRoom(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(
            path = RestApiPaths.PARTIAL_UPDATE_ROOM,
            consumes = "application/json-patch+json")
    @Operation(
            summary = "Partially update room REST API",
            description = "Admin partial update of room data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<PartialUpdateRoomOutput> partialUpdateRoom(@PathVariable @Schema(example = "15") String roomId,
                                                                     @RequestBody @Valid PartialUpdateRoomInput input) {
        PartialUpdateRoomOutput result = systemService.partialUpdateRoom(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(RestApiPaths.DELETE_ROOM)
    @Operation(
            summary = "Delete room REST API",
            description = "Deletes a room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<DeleteRoomOutput> deleteRoom(@PathVariable UUID roomId) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .roomId(roomId)
                .build();

        DeleteRoomOutput result = systemService.deleteRoom(input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
