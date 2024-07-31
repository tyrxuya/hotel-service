package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.UserNotFoundException;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoom;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
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
public class DeleteRoomOperation extends BaseOperation implements DeleteRoom {
    private final RoomRepository roomRepository;

    public DeleteRoomOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, RoomRepository roomRepository) {
        super(validator, conversionService, errorMapper);
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorOutput, DeleteRoomOutput> process(DeleteRoomInput input) {
        return Try.of(() -> {
            log.info("start deleteRoom input: {}", input);

            validate(input);

            roomRepository.deleteById(UUID.fromString(input.getRoomId()));

            DeleteRoomOutput result = DeleteRoomOutput.builder().build();

            log.info("end deleteRoom result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }
}
