package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.BookRoomService;
import com.tinqinacademy.hotel.api.contracts.operations.CheckRoomAvailabilityService;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckRoomAvailabilityServiceImpl implements CheckRoomAvailabilityService {
    private final BookingRepository bookingRepository;

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
}
