package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.GetRoomInfoService;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetRoomInfoServiceImpl implements GetRoomInfoService {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final ConversionService conversionService;

    @Override
    public GetRoomByIdOutput getRoomInfo(GetRoomByIdInput input) {
        log.info("start getRoomInfo input: {}", input);

        Room room = findRoomByInput(input);

        Booking booking = findBookingByRoom(room);

        GetRoomByIdOutput result = conversionService.convert(room, GetRoomByIdOutput.class);

        List<LocalDate> datesOccupied = getDatesOccupiedFromBooking(booking);

        result.setDatesOccupied(datesOccupied);

        log.info("end getRoomInfo result: {}", result);

        return result;
    }

    private Booking findBookingByRoom(Room room) {
        return bookingRepository.findByRoomId(room.getId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    private Room findRoomByInput(GetRoomByIdInput input) {
        return roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    private List<LocalDate> getDatesOccupiedFromBooking(Booking booking) {
        List<LocalDate> datesOccupied = new ArrayList<>();

        datesOccupied.add(booking.getStartDate());
        datesOccupied.add(booking.getEndDate());

        return datesOccupied;
    }
}
