package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.UpdateRoomService;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateRoomServiceImpl implements UpdateRoomService {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("start updateRoom input: {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found!"));

        room.setBathroomType(input.getBathroomType());
        room.setFloor(input.getFloor());
        room.setPrice(input.getPrice());
        room.setNumber(input.getRoomNo());

        List<Bed> beds = new ArrayList<>();
        input.getBedSizes().forEach(bedSize -> {
            Bed bed = bedRepository.findBedByBedSize(bedSize)
                    .orElseThrow(() -> new IllegalArgumentException("Bed not found"));
            beds.add(bed);
        });

        room.setBeds(beds);

        roomRepository.save(room);

        //roomRepository.update(room);

        UpdateRoomOutput result = UpdateRoomOutput.builder()
                .roomId(room.getId())
                .build();

        log.info("end updateRoom result: {}", result);

        return result;
    }
}
