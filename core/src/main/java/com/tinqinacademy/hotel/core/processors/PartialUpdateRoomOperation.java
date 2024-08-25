package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.BedNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.BookingNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoom;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
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

import java.util.*;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class PartialUpdateRoomOperation extends BaseOperation implements PartialUpdateRoom {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    public PartialUpdateRoomOperation(Validator validator,
                                      ConversionService conversionService,
                                      ErrorMapper errorMapper,
                                      RoomRepository roomRepository,
                                      BedRepository bedRepository) {
        super(validator, conversionService, errorMapper);
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }

    @Override
    public Either<ErrorOutput, PartialUpdateRoomOutput> process(PartialUpdateRoomInput input) {
        return Try.of(() -> {
            log.info("Start process method in PartialUpdateRoomOperation. Input: {}", input);

            validate(input);

            Room room = getRoomFromRepository(input);
            log.info("Room {} found in repository.", room);

            setNonNullAttributes(input, room);
            log.info("Updated all non-null values.");

            roomRepository.save(room);
            log.info("Room {} saved in repository.", room);

            PartialUpdateRoomOutput result = PartialUpdateRoomOutput.builder()
                    .roomId(room.getId().toString())
                    .build();

            log.info("End process method in PartialUpdateRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, BedNotFoundException.class),
                        customCase(throwable, HttpStatus.NOT_FOUND, RoomNotFoundException.class),
                        validateCase(throwable, HttpStatus.BAD_REQUEST)
                ));
    }

    private void setNonNullAttributes(PartialUpdateRoomInput input, Room room) {
        Optional.ofNullable(input.getRoomNo())
                .ifPresent(room::setNumber);

        Optional.ofNullable(input.getBathroomType())
                .ifPresent(bathroomType -> room.setBathroomType(BathroomType.getBathroomType(bathroomType)));

        Optional.ofNullable(input.getFloor())
                .ifPresent(room::setFloor);

        Optional.ofNullable(input.getPrice())
                .ifPresent(room::setPrice);

        Optional.ofNullable(input.getBedSizes())
                .ifPresent(bedSizes -> {
                    List<BedSize> bedSizesList = new ArrayList<>();
                    bedSizes.forEach(bedSize -> bedSizesList.add(BedSize.getBedSize(bedSize)));
                    List<Bed> beds = getBedsFromRepository(bedSizesList);
                    room.setBeds(beds);
                });
    }

    private List<Bed> getBedsFromRepository(List<BedSize> bedSizes) {
        List<Bed> beds = new ArrayList<>();
        bedSizes.forEach(bedSize -> beds.add(
                        bedRepository.findBedByBedSize(bedSize)
                                .orElseThrow(() -> {
                                    log.warn("Bed with bed size {} not found.", bedSize);
                                    return new BedNotFoundException();
                                })
                )
        );

        return new ArrayList<>(beds);
    }

    private Room getRoomFromRepository(PartialUpdateRoomInput input) {
        return roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> {
                    log.warn("Room with id {} not found.", input.getRoomId());
                    return new RoomNotFoundException();
                });
    }
}
