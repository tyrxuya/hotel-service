package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.RegisterVisitorService;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterVisitorServiceImpl implements RegisterVisitorService {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;

    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("start registerVisitor input: {}", input);

        Booking booking = bookingRepository.findById(input.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("booking not found"));

        List<Guest> guests = initializeGuestList(input.getHotelVisitors());

        guestRepository.saveAll(guests);

        guests.forEach(guest -> booking.getGuests().add(guest));

        bookingRepository.save(booking);

        RegisterVisitorOutput result = RegisterVisitorOutput.builder().build();

        log.info("end registerVisitor result: {}", result);

        return result;
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
