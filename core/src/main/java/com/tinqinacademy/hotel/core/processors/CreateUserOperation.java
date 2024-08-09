package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.UserNotFoundException;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUser;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserOutput;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;

@Service
@Slf4j
public class CreateUserOperation extends BaseOperation implements CreateUser {
    private final UserRepository userRepository;

    public CreateUserOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, UserRepository userRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorOutput, CreateUserOutput> process(CreateUserInput input) {
        return Try.of(() -> {
            log.info("Start process method in CreateUserOperation. Input: {}", input);

            validate(input);

            User user = conversionService.convert(input, User.class);

            userRepository.save(user);
            log.info("User {} saved in repository", user);

            CreateUserOutput result = CreateUserOutput.builder()
                    .userId(user.getId())
                    .build();

            log.info("End process method in CreateUserOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }
}
