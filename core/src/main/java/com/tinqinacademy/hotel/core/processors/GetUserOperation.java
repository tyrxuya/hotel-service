package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.BookingNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.exceptions.UserNotFoundException;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserInput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUser;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;
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

import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class GetUserOperation extends BaseOperation implements GetUser {
    private final UserRepository userRepository;

    public GetUserOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, UserRepository userRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorOutput, GetUserOutput> process(GetUserInput input) {
        return Try.of(() -> {
            log.info("Start process method in GetUserOperation. Input: {}", input);

            validate(input);

            User user = getUserFromRepository(input);
            log.info("User {} found in repository.", user);

            GetUserOutput result = conversionService.convert(user, GetUserOutput.class);

            log.info("End process method in GetUserOperation. Result: {}", result);
            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, UserNotFoundException.class),
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }

    private User getUserFromRepository(GetUserInput input) {
        return userRepository.findById(UUID.fromString(input.getUserId()))
                .orElseThrow(() -> {
                    log.warn("User {} not found in repository.", input.getUserId());
                    return new UserNotFoundException();
                });
    }
}
