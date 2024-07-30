package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.PartialUpdateRoomService;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartialUpdateRoomServiceImpl implements PartialUpdateRoomService {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) {
        log.info("start partialUpdateRoom input: {}", input);

        PartialUpdateRoomOutput result = PartialUpdateRoomOutput.builder().build();

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new IllegalArgumentException("Room not found!"));

        Optional.ofNullable(input.getRoomNo())
                .ifPresent(room::setNumber);

        Optional.ofNullable(input.getBathroomType())
                .ifPresent(room::setBathroomType);

        Optional.ofNullable(input.getFloor())
                .ifPresent(room::setFloor);

        Optional.ofNullable(input.getPrice())
                .ifPresent(room::setPrice);

        Optional.ofNullable(input.getBedSizes())
                .ifPresent(bedSizes -> {
                    List<Bed> beds = new ArrayList<>();
                    bedSizes.forEach(bedSize -> beds.add(
                                    bedRepository.findBedByBedSize(bedSize)
                                            .orElseThrow(() -> new IllegalArgumentException("Bed not found"))
                            )
                    );
                    room.setBeds(beds);
                });

        roomRepository.save(room);

        log.info("end partialUpdateRoom result: {}", result);

        return result;
    }
}
