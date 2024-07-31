package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.BookingNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitor;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class RegisterVisitorOperation extends BaseOperation implements RegisterVisitor {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;

    public RegisterVisitorOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, BookingRepository bookingRepository, GuestRepository guestRepository) {
        super(validator, conversionService, errorMapper);
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public Either<ErrorOutput, RegisterVisitorOutput> process(RegisterVisitorInput input) {
        return Try.of(() -> {
            log.info("start registerVisitor input: {}", input);

            validate(input);

            Booking booking = getBookingFromRepository(input);

            List<Guest> guests = initializeGuestList(input.getHotelVisitors());

            guestRepository.saveAll(guests);

            guests.forEach(guest -> booking.getGuests().add(guest));

            bookingRepository.save(booking);

            RegisterVisitorOutput result = RegisterVisitorOutput.builder().build();

            log.info("end registerVisitor result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, BookingNotFoundException.class),
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }

    private Booking getBookingFromRepository(RegisterVisitorInput input) {
        return bookingRepository.findById(UUID.fromString(input.getBookingId()))
                .orElseThrow(BookingNotFoundException::new);
    }

    private List<Guest> initializeGuestList(List<HotelVisitorInput> hotelVisitors) {
        List<Guest> guests = new ArrayList<>();

        hotelVisitors
                .forEach(guest ->
                        guests.add(Guest.builder()
                                .firstName(guest.getFirstName())
                                .lastName(guest.getLastName())
                                .phone(guest.getPhoneNo())
                                .birthday(guest.getBirthday())
                                .civilNumber(guest.getCivilNumber())
                                .idIssueAuthority(guest.getIdIssueAuthority())
                                .idIssueDate(guest.getIdIssueDate())
                                .idValidity(guest.getIdValidity())
                                .build()));

        return guests;
    }
}
