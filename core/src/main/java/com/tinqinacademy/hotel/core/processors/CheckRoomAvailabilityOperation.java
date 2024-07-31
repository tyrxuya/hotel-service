package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.errors.Errors;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomAvailability;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
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
import static io.vavr.Predicates.instanceOf;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CheckRoomAvailabilityOperation extends BaseOperation implements CheckRoomAvailability {
    private final BookingRepository bookingRepository;

    public CheckRoomAvailabilityOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, CheckRoomsOutput> process(CheckRoomsInput input) {
        return Try.of(() -> {
            log.info("start checkRoomAvailability input: {}", input);

            validate(input);

            List<UUID> roomIds = getRoomIds(input);

            CheckRoomsOutput result = CheckRoomsOutput.builder()
                    .idList(roomIds)
                    .build();

            log.info("end checkRoomAvailability result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }

    private List<UUID> getRoomIds(CheckRoomsInput input) {
        return bookingRepository.searchRooms(input.getStartDate(),
                input.getEndDate(),
                input.getBedSize(),
                input.getBathroomType());
    }
}
