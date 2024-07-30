package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.BookRoomService;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookRoomServiceImpl implements BookRoomService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("start bookRoom input: {}", input);

        User user = getUserFromRepository(input);

        Room room = getRoomFromRepository(input);

        checkIsRoomPresent(input);

        Booking booking = createBooking(input, room, user);

        bookingRepository.save(booking);

        BookRoomOutput result = BookRoomOutput.builder().build();

        log.info("end bookRoom result: {}", result);

        return result;
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

        return daysDecimal.multiply(room.getPrice());
    }

    private void checkIsRoomPresent(BookRoomInput input) {
        bookingRepository.searchRooms(input.getStartDate(), input.getEndDate())
                .stream()
                .filter(id -> UUID.fromString(input.getRoomId()).equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    private Room getRoomFromRepository(BookRoomInput input) {
        return roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    private User getUserFromRepository(BookRoomInput input) {
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
