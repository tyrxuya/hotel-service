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

import java.util.List;
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

//        User user = User.builder()
//                .id(UUID.randomUUID())
//                .username(input.getFirstName() + "_" + input.getLastName())
//                .password("random")
//                .build();

//        userRepository.save(user);

        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        List<UUID> occupiedRooms = bookingRepository.searchRooms(input.getStartDate(),
                input.getEndDate());

        if (occupiedRooms.contains(room.getId())) {
            throw new IllegalArgumentException("Room already occupied");
        }

        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .room(room)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .user(user)
                .price(room.getPrice())
                .build();

        bookingRepository.save(booking);

        BookRoomOutput result = BookRoomOutput.builder().build();

        log.info("end bookRoom result: {}", result);

        return result;
    }
}
