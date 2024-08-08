package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.RestApiPaths;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUser;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUser;
import com.tinqinacademy.hotel.api.operations.getuser.GetUser;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserOutput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserInput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserInput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User REST APIs")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final CreateUser createUserOperation;
    private final DeleteUser deleteUserOperation;
    private final GetUser getUserOperation;
    private final ObjectMapper objectMapper;

    @PostMapping(RestApiPaths.CREATE_USER)
    public ResponseEntity<?> createUser(@RequestBody CreateUserInput input) {
        Either<ErrorOutput, CreateUserOutput> result = createUserOperation.process(input);

        return getOutput(result, HttpStatus.CREATED);
    }

    @DeleteMapping(RestApiPaths.DELETE_USER)
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        DeleteUserInput input = DeleteUserInput.builder()
                .userId(userId)
                .build();

        Either<ErrorOutput, DeleteUserOutput> result = deleteUserOperation.process(input);

        return getOutput(result, HttpStatus.OK);
    }

    @GetMapping(RestApiPaths.GET_USER)
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        GetUserInput input = GetUserInput.builder()
                .userId(userId)
                .build();

        Either<ErrorOutput, GetUserOutput> result = getUserOperation.process(input);

        return getOutput(result, HttpStatus.OK);
    }
}
