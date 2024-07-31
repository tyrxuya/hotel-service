package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
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

import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class UnbookRoomOperation extends BaseOperation implements UnbookRoom {
    private final BookingRepository bookingRepository;

    public UnbookRoomOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, UnbookRoomOutput> process(UnbookRoomInput input) {
        return Try.of(() -> {
            log.info("start unbookRoom input: {}", input);

            validate(input);

            bookingRepository.deleteById(UUID.fromString(input.getBookingId()));

            UnbookRoomOutput result = UnbookRoomOutput.builder().build();

            log.info("end unbookRoom result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }
}
