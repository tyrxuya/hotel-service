package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.operations.hotelvisitor.HotelVisitorInput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.services_.SystemService;
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
@Tag(name = "System REST APIs")
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {
    private final SystemService systemService;

    @PostMapping("/register")
    @Operation(
            summary = "Register visitor REST API",
            description = "Registers a visitor as room renter."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<RegisterVisitorOutput> registerVisitor(@RequestBody RegisterVisitorInput input) {
        return new ResponseEntity<>(systemService.registerVisitor(input), HttpStatus.CREATED);
    }

    @GetMapping("/register")
    @Operation(
            summary = "Info visitor REST API",
            description = "Provides a report based on various criteria. Provides info when room was occupied and by whom. Can report when a user has occupied rooms."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<GetRegisteredVisitorsOutput> getVisitorsInfo(@RequestParam(required = false) @Schema(example = "101b") String roomNo,
                                                                       @RequestParam(required = false) @Schema(example = "2022-05-22") LocalDate startDate,
                                                                       @RequestParam(required = false) @Schema(example = "2022-05-25") LocalDate endDate,
                                                                       @RequestParam(required = false) @Schema(example = "vanio") String firstName,
                                                                       @RequestParam(required = false) @Schema(example = "georgiev") String lastName,
                                                                       @RequestParam(required = false) @Schema(example = "+359887839281") String phoneNo,
                                                                       @RequestParam(required = false) @Schema(example = "0348888888") String idNo,
                                                                       @RequestParam(required = false) @Schema(example = "mvr varna") String idIssueAuthority,
                                                                       @RequestParam(required = false) @Schema(example = "2015-05-22") LocalDate idIssueDate) {
        HotelVisitorInput visitor = HotelVisitorInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .idNo(idNo)
                .idIssueAuthority(idIssueAuthority)
                .idIssueDate(idIssueDate)
                .build();

        GetRegisteredVisitorsInput input = GetRegisteredVisitorsInput.builder()
                .roomNo(roomNo)
                .visitor(visitor)
                .build();

        return new ResponseEntity<>(systemService.getVisitorsInfo(input), HttpStatus.OK);
    }

    @PostMapping("/room")
    @Operation(
            summary = "Create room REST API",
            description = "Admin creates a new room with the specified parameters."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<CreateRoomOutput> createRoom(@RequestBody CreateRoomInput input) {
        return new ResponseEntity<>(systemService.createRoom(input), HttpStatus.CREATED);
    }

    @PutMapping("/room/{roomId}")
    @Operation(
            summary = "Update room REST API",
            description = "Admin updates the info regarding a certain room."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<UpdateRoomOutput> updateRoom(@PathVariable @Schema(example = "15") String roomId,
                                                       @RequestBody UpdateRoomInput input) {
        return new ResponseEntity<>(systemService.updateRoom(input), HttpStatus.OK);
    }

    @PatchMapping("/room/{roomId}")
    @Operation(
            summary = "Partially update room REST API",
            description = "Admin partial update of room data."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<PartialUpdateRoomOutput> partialUpdateRoom(@PathVariable @Schema(example = "15") String roomId,
                                                                     @RequestBody PartialUpdateRoomInput input) {
        return new ResponseEntity<>(systemService.partialUpdateRoom(input), HttpStatus.OK);
    }

    @DeleteMapping("/room/{roomId}")
    @Operation(
            summary = "Delete room REST API",
            description = "Deletes a room."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<DeleteRoomOutput> deleteRoom(@PathVariable @Schema(example = "15") String roomId) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(systemService.deleteRoom(input), HttpStatus.OK);
    }
}
