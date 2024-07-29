package com.tinqinacademy.hotel.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.contracts.operations.CreateUserService;
import com.tinqinacademy.hotel.api.contracts.operations.DeleteUserService;
import com.tinqinacademy.hotel.api.contracts.operations.GetUserService;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserOutput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserInput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserInput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "User REST APIs")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserService createUserService;
    private final DeleteUserService deleteUserService;
    private final GetUserService getUserService;
    private final ObjectMapper objectMapper;

    @PostMapping(RestApiPaths.CREATE_USER)
    public ResponseEntity<CreateUserOutput> createUser(@RequestBody CreateUserInput input) {
        CreateUserOutput output = createUserService.createUser(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @DeleteMapping(RestApiPaths.DELETE_USER)
    public ResponseEntity<DeleteUserOutput> deleteUser(@PathVariable String userId) {
        DeleteUserInput input = DeleteUserInput.builder()
                .userId(UUID.fromString(userId))
                .build();

        DeleteUserOutput output = deleteUserService.deleteUser(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping(RestApiPaths.GET_USER)
    public ResponseEntity<GetUserOutput> getUser(@PathVariable String userId) {
        GetUserInput input = GetUserInput.builder()
                .userId(UUID.fromString(userId))
                .build();

        GetUserOutput output = getUserService.getUser(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
