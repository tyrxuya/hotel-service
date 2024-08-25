package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.UserNotFoundException;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoom;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
public class BookRoomOperation extends BaseOperation implements BookRoom {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public BookRoomOperation(Validator validator,
                             ConversionService conversionService,
                             ErrorMapper errorMapper,
                             RoomRepository roomRepository,
                             BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, BookRoomOutput> process(BookRoomInput input) {
        return Try.of(() -> {
            log.info("Start process method in BookRoomOperation. Input: {}", input);

            validate(input);

            Room room = getRoomFromRepository(input);
            log.info("Room {} found.", room);

            checkIsRoomPresent(input);
            log.info("Room {} is available for booking.", room);

            Booking booking = createBooking(input, room);
            log.info("Booking {} created.", booking);

            bookingRepository.save(booking);
            log.info("Booking {} saved in repository.", booking);

            BookRoomOutput result = BookRoomOutput.builder()
                    .bookingId(booking.getId().toString())
                    .build();

            log.info("End process method in BookRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, RoomNotFoundException.class),
                        customCase(throwable, HttpStatus.NOT_FOUND, UserNotFoundException.class),
                        validateCase(throwable, HttpStatus.BAD_REQUEST)
                ));
    }

    private Booking createBooking(BookRoomInput input, Room room) {
        return Booking.builder()
                .room(room)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .userId(UUID.fromString(input.getUserId()))
                .price(getFinalPrice(input, room))
                .build();
    }

    private BigDecimal getFinalPrice(BookRoomInput input, Room room) {
        Long days = ChronoUnit.DAYS.between(input.getStartDate(), input.getEndDate());

        BigDecimal daysDecimal = BigDecimal.valueOf(days);

        BigDecimal finalPrice = daysDecimal.multiply(room.getPrice());

        return finalPrice;
    }

    private void checkIsRoomPresent(BookRoomInput input) {
//        Boolean present = bookingRepository.searchRooms(input.getStartDate(), input.getEndDate())
//                .stream()
//                .anyMatch(id -> UUID.fromString(input.getRoomId()).equals(id));
        long count = bookingRepository.countByRoomAndDates(UUID.fromString(input.getRoomId()), input.getStartDate(), input.getEndDate());

        if (count > 0) {
            throw new RoomNotFoundException();
        }
    }

    private Room getRoomFromRepository(BookRoomInput input) {
        return roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> {
                    log.warn("Room with id {} not found", input.getRoomId());
                    return new RoomNotFoundException();
                });
    }
}
