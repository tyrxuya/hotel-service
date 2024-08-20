package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.errors.Errors;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.UserNotFoundException;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoom;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookRoomOperation extends BaseOperation implements BookRoom {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public BookRoomOperation(Validator validator,
                             ConversionService conversionService,
                             ErrorMapper errorMapper,
                             UserRepository userRepository,
                             RoomRepository roomRepository,
                             BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, BookRoomOutput> process(BookRoomInput input) {
        return Try.of(() -> {
            log.info("Start process method in BookRoomOperation. Input: {}", input);

            validate(input);

            User user = getUserFromRepository(input);
            log.info("User {} found.", user);

            Room room = getRoomFromRepository(input);
            log.info("Room {} found.", room);

            checkIsRoomPresent(input);
            log.info("Room {} is available for booking.", room);

            Booking booking = createBooking(input, room, user);
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
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.INTERNAL_SERVER_ERROR)
                ));
    }

    private Booking createBooking(BookRoomInput input, Room room, User user) {
        return Booking.builder()
                .room(room)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .user(user)
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
        Boolean present = bookingRepository.searchRooms(input.getStartDate(), input.getEndDate())
                .stream()
                .anyMatch(id -> UUID.fromString(input.getRoomId()).equals(id));

        if (present) {
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

    private User getUserFromRepository(BookRoomInput input) {
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> {
                    log.warn("User with username {} not found.", input.getUsername());
                    return new UserNotFoundException();
                });
    }
}
