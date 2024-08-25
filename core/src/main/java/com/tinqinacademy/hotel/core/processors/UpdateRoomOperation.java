package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.BedNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoom;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class UpdateRoomOperation extends BaseOperation implements UpdateRoom {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    public UpdateRoomOperation(Validator validator,
                               ConversionService conversionService,
                               ErrorMapper errorMapper,
                               RoomRepository roomRepository,
                               BedRepository bedRepository) {
        super(validator, conversionService, errorMapper);
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }

    @Override
    public Either<ErrorOutput, UpdateRoomOutput> process(UpdateRoomInput input) {
        return Try.of(() -> {
            log.info("Start process method in UpdateRoomOperation. Input: {}", input);

            validate(input);

            Room room = getRoomFromRepository(input);
            log.info("Room {} found in repository.", room);

            updateRoom(input, room);

            log.info("Input: {}", input);

            roomRepository.save(room);
            log.info("Room {} updated in repository.", room);

            //roomRepository.update(room);

            UpdateRoomOutput result = UpdateRoomOutput.builder()
                    .roomId(room.getId().toString())
                    .build();

            log.info("End process in UpdateRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, BedNotFoundException.class),
                        customCase(throwable, HttpStatus.NOT_FOUND, RoomNotFoundException.class),
                        validateCase(throwable, HttpStatus.BAD_REQUEST)
                ));
    }

    private void updateRoom(UpdateRoomInput input, Room room) {
        room.setBathroomType(BathroomType.getBathroomType(input.getBathroomType()));
        room.setFloor(input.getFloor());
        room.setPrice(input.getPrice());
        room.setNumber(input.getRoomNo());

        List<Bed> beds = new ArrayList<>();
        input.getBedSizes().forEach(bedSize -> {
            Bed bed = getBedByBedSizeFromRepository(BedSize.getBedSize(bedSize));
            beds.add(bed);
        });

        room.setBeds(beds);
    }

    private Room getRoomFromRepository(UpdateRoomInput input) {
        return roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> {
                    log.warn("Room with id {} not found.", input.getRoomId());
                    return new RoomNotFoundException();
                });
    }

    private Bed getBedByBedSizeFromRepository(BedSize bedSize) {
        return bedRepository.findBedByBedSize(bedSize)
                .orElseThrow(() -> {
                    log.warn("Bed {} not found.", bedSize);
                    return new BedNotFoundException();
                });
    }
}
