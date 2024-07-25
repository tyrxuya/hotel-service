package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.contracts.HotelService;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.core.converters.RoomToGetRoomByIdOutputConverter;
import com.tinqinacademy.hotel.persistence.repositories.*;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final UserRepository userRepository;
    private final RoomToGetRoomByIdOutputConverter roomToGetRoomByIdOutputConverter;

    @Override
    public CheckRoomsOutput checkRoomAvailability(CheckRoomsInput input) {
        log.info("start checkRoomAvailability input: {}", input);

        List<UUID> roomIds = bookingRepository.searchRooms(input.getStartDate(),
                input.getEndDate(),
                input.getBedSize(),
                input.getBathroomType());

        CheckRoomsOutput result = CheckRoomsOutput.builder()
                .idList(roomIds)
                .build();

        log.info("end checkRoomAvailability result: {}", result);

        return result;
    }

    @Override
    public GetRoomByIdOutput getRoomInfo(GetRoomByIdInput input) {
        log.info("start getRoomInfo input: {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Optional<Booking> foundBooking = bookingRepository.findByRoomId(room.getId());

        List<LocalDate> datesOccupied = new ArrayList<>();
        if (foundBooking.isPresent()) {
            Booking booking = foundBooking.get();
            datesOccupied.add(booking.getStartDate());
            datesOccupied.add(booking.getEndDate());
        }

        GetRoomByIdOutput result = roomToGetRoomByIdOutputConverter.convert(room);

        result.toBuilder()
                .datesOccupied(datesOccupied)
                .build();

        log.info("end getRoomInfo result: {}", result);

        return result;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("start bookRoom input: {}", input);

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(input.getFirstName() + "_" + input.getLastName())
                .password("random")
                .build();

        BigDecimal price = roomRepository.getPriceById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .room(room)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .user(user)
                .price(price)
                .build();

        bookingRepository.save(booking);

        BookRoomOutput result = BookRoomOutput.builder().build();

        log.info("end bookRoom result: {}", result);

        return result;
    }

    @Override
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("start unbookRoom input: {}", input);

        bookingRepository.deleteById(input.getBookingId());

        UnbookRoomOutput result = UnbookRoomOutput.builder().build();

        log.info("end unbookRoom result: {}", result);

        return result;
    }
}
