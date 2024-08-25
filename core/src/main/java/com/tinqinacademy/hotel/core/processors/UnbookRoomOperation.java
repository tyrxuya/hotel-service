package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.BookingNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
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

    public UnbookRoomOperation(Validator validator,
                               ConversionService conversionService,
                               ErrorMapper errorMapper,
                               BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, UnbookRoomOutput> process(UnbookRoomInput input) {
        return Try.of(() -> {
            log.info("Start process method in UnbookRoomOperation. Input: {}", input);

            validate(input);

            Booking booking = getBookingByInput(input);

            bookingRepository.delete(booking);
            log.info("Booking with id {} deleted from repository", input.getBookingId());

            UnbookRoomOutput result = UnbookRoomOutput.builder()
                    .bookingId(booking.getId().toString())
                    .build();

            log.info("End process method in UnbookRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        customCase(throwable, HttpStatus.NOT_FOUND, BookingNotFoundException.class)
                ));
    }

    private Booking getBookingByInput(UnbookRoomInput input) {
        UUID id = UUID.fromString(input.getBookingId());
        UUID userId = UUID.fromString(input.getUserId());
        return bookingRepository.findByIdAndUserId(id, userId)
                .orElseThrow(BookingNotFoundException::new);
    }
}
