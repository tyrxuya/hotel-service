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

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Optional<Booking> foundBooking = bookingRepository.findByRoomId(room.getId());

        List<LocalDate> datesOccupied = new ArrayList<>();
        if (foundBooking.isPresent()) {
            Booking booking = foundBooking.get();
            datesOccupied.add(booking.getStartDate());
            datesOccupied.add(booking.getEndDate());
        }

        GetRoomByIdOutput result = conversionService.convert(room, GetRoomByIdOutput.class);

        result.setDatesOccupied(datesOccupied);

        log.info("end getRoomInfo result: {}", result);

        return result;
    }
}
