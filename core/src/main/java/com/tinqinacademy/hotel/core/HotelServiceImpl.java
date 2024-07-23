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
import com.tinqinacademy.hotel.persistence.contracts.*;
import com.tinqinacademy.hotel.persistence.models.Booking;
import com.tinqinacademy.hotel.persistence.models.Room;
import com.tinqinacademy.hotel.persistence.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public CheckRoomsOutput checkRoomAvailability(CheckRoomsInput input) {
        log.info("start checkRoomAvailability input: {}", input);

        List<String> roomIds = new ArrayList<>();
        roomIds.add("123");
        roomIds.add("456");
        roomIds.add("789");

        CheckRoomsOutput result = CheckRoomsOutput.builder()
                .idList(roomIds)
                .build();

        log.info("end checkRoomAvailability result: {}", result);

        return result;
    }

    @Override
    public GetRoomByIdOutput getRoomInfo(GetRoomByIdInput input) {
        log.info("start getRoomInfo input: {}", input);

//        List<LocalDate> datesOccupied = new ArrayList<>();
//        datesOccupied.add(LocalDate.of(2020, 1, 1));
//        datesOccupied.add(LocalDate.of(2020, 1, 2));
//        datesOccupied.add(LocalDate.of(2020, 1, 3));
//        datesOccupied.add(LocalDate.of(2020, 1, 4));
//        datesOccupied.add(LocalDate.of(2020, 1, 5));
//        datesOccupied.add(LocalDate.of(2020, 1, 6));
//
//        GetRoomByIdOutput result = GetRoomByIdOutput.builder()
//                .roomId(input.getRoomId())
//                .price(BigDecimal.valueOf(123124))
//                .floor(5)
//                .bedSize(BedSize.getBedSize("kingSize"))
//                .bathroomType(BathroomType.getBathroomType("private"))
//                .bedCount(10)
//                .datesOccupied(datesOccupied)
//                .build();

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        GetRoomByIdOutput result = GetRoomByIdOutput.builder()
                .roomId(room.getId())
                .price(room.getPrice())
                .floor(room.getFloor())
                .bathroomType(room.getBathroomType())
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

        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .roomId(input.getRoomId())
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .userId(user.getId())
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

        UnbookRoomOutput result = UnbookRoomOutput.builder().build();

        log.info("end unbookRoom result: {}", result);

        return result;
    }
}
