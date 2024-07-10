package com.tinqinacademy.hotel;

import com.tinqinacademy.hotel.contracts.RoomService;
import com.tinqinacademy.hotel.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    //@Autowired
//    public RoomController(RoomService roomService) {
//        this.roomService = roomService;
//    }

    @PostMapping("/book")
    @Operation(
            summary = "Book room REST API",
            description = "Books a room"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "403", description = "unauthorized access"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    }
    )
    public ResponseEntity<String> book(@RequestBody Test test) {
        return new ResponseEntity<>(roomService.bookRoom(test), OK);
    }

    @GetMapping("")
    @Operation(
            summary = "Check room REST API",
            description = "Checks a room if it's available"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "403", description = "unauthorized access"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    }
    )
    public ResponseEntity<String> check(@RequestParam(value = "roomNumber") Integer roomNumber) {
        return new ResponseEntity<>(roomService.checkRoom(roomNumber), OK);
    }

    @PostMapping("")
    @Operation(
            summary = "Add room REST API",
            description = "Adds a room"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "403", description = "unauthorized access"),
            @ApiResponse(responseCode = "404", description = "ok"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    }
    )
    public ResponseEntity<RoomOutput> add(@RequestBody RoomInput input) {
        return new ResponseEntity<>(roomService.addRoom(input), CREATED);
    }

    @DeleteMapping("")
    @Operation(
            summary = "Remove room REST API",
            description = "Removes a room"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "403", description = "unauthorized access"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    }
    )
    public ResponseEntity<String> remove(@RequestParam(value = "roomNumber") Integer roomNumber) {
        return new ResponseEntity<>(roomService.deleteRoom(roomNumber), OK);
    }

    @PutMapping("")
    @Operation(
            summary = "Edit room REST API",
            description = "Edits a room"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "403", description = "unauthorized access"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    }
    )
    public ResponseEntity<String> edit(@RequestParam(value = "roomNumber") Integer roomNumber) {
        return new ResponseEntity<>(roomService.editRoom(roomNumber), OK);
    }

    @GetMapping("/{myFloor}")
    public ResponseEntity<String> bookRoom(@PathVariable(name = "myFloor") Integer floor,
                                           @RequestParam(value = "bedSize") String bedSize) {
        BookRoom room = BookRoom.builder()
                .floor(floor)
                .bedSize(BedSize.getBedSize(bedSize))
                .build();
        return new ResponseEntity<>(roomService.reserveRoom(room), OK);
    }
}
