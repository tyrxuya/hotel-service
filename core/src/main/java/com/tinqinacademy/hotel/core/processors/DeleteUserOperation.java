package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserInput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUser;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;
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

@Service
@Slf4j
public class DeleteUserOperation extends BaseOperation implements DeleteUser {
    private final UserRepository userRepository;

    public DeleteUserOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, UserRepository userRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorOutput, DeleteUserOutput> process(DeleteUserInput input) {
        return Try.of(() -> {
            log.info("start deleteUser input: {}", input);

            validate(input);

            userRepository.deleteById(UUID.fromString(input.getUserId()));

            DeleteUserOutput result = DeleteUserOutput.builder().build();

            log.info("end deleteUser result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }
}
