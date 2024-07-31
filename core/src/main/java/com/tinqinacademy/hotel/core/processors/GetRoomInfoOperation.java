package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.BookingNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomInfo;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class GetRoomInfoOperation extends BaseOperation implements GetRoomInfo {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public GetRoomInfoOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, RoomRepository roomRepository, BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, GetRoomByIdOutput> process(GetRoomByIdInput input) {
        return Try.of(() -> {
            log.info("start getRoomInfo input: {}", input);

            validate(input);

            Room room = findRoomByInput(input);

            Booking booking = findBookingByRoom(room);

            GetRoomByIdOutput result = conversionService.convert(room, GetRoomByIdOutput.class);

            List<LocalDate> datesOccupied = getDatesOccupiedFromBooking(booking);

            result.setDatesOccupied(datesOccupied);

            log.info("end getRoomInfo result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, BookingNotFoundException.class),
                        customCase(throwable, HttpStatus.NOT_FOUND, RoomNotFoundException.class),
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }

    private Booking findBookingByRoom(Room room) {
        return bookingRepository.findByRoomId(room.getId())
                .orElseThrow(BookingNotFoundException::new);
    }

    private Room findRoomByInput(GetRoomByIdInput input) {
        return roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(RoomNotFoundException::new);
    }

    private List<LocalDate> getDatesOccupiedFromBooking(Booking booking) {
        List<LocalDate> datesOccupied = new ArrayList<>();

        datesOccupied.add(booking.getStartDate());
        datesOccupied.add(booking.getEndDate());

        return datesOccupied;
    }
}
