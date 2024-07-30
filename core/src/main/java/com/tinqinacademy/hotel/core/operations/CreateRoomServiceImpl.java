package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.CreateRoomService;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateRoomServiceImpl implements CreateRoomService {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final ConversionService conversionService;

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("start createRoom input: {}", input);

        List<Bed> beds = createBeds(input);

        Room room = conversionService.convert(input, Room.class);

        room.setBeds(beds);

        roomRepository.save(room);

        CreateRoomOutput result = CreateRoomOutput.builder()
                .roomId(room.getId())
                .build();

        log.info("end createRoom result: {}", result);

        return result;
    }

    private List<Bed> createBeds(CreateRoomInput input) {
        List<BedSize> bedSizes = new ArrayList<>(input.getBedSizes());

        List<Bed> beds = new ArrayList<>();
        bedSizes.forEach(bedSize -> {
            Bed bed = bedRepository.findBedByBedSize(bedSize)
                    .orElseThrow(() -> new IllegalArgumentException("Bed not found"));
            beds.add(bed);
        });

        return beds;
    }
}
