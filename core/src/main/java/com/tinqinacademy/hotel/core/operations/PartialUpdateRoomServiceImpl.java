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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found!"));

        if (Objects.nonNull(input.getRoomNo())) {
            room.setNumber(input.getRoomNo());
        }

        if (Objects.nonNull(input.getBathroomType())) {
            room.setBathroomType(input.getBathroomType());
        }

        if (Objects.nonNull(input.getFloor())) {
            room.setFloor(input.getFloor());
        }

        if (Objects.nonNull(input.getPrice())) {
            room.setPrice(input.getPrice());
        }

        if (Objects.nonNull(input.getBedSizes())) {
            List<BedSize> bedSizes = new ArrayList<>(input.getBedSizes());
            List<Bed> beds = new ArrayList<>();
            input.getBedSizes().forEach(bedSize -> beds.add(
                            bedRepository.findBedByBedSize(bedSize)
                                    .orElseThrow(() -> new IllegalArgumentException("Bed not found"))
                    )
            );
            room.setBeds(beds);
        }

        roomRepository.save(room);

        log.info("end partialUpdateRoom result: {}", result);

        return result;
    }
}
