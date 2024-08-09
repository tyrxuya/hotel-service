package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.BedNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoom;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.entities.Room;
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

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class CreateRoomOperation extends BaseOperation implements CreateRoom {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    public CreateRoomOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, BedRepository bedRepository, RoomRepository roomRepository) {
        super(validator, conversionService, errorMapper);
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorOutput, CreateRoomOutput> process(CreateRoomInput input) {
        return Try.of(() -> {
            log.info("Start process method in CreateRoomOperation. Input: {}", input);

            validate(input);

            List<Bed> beds = createBeds(input);
            log.info("Created Bed entities {} based on input: {}", beds, input.getBedSizes());

            Room room = conversionService.convert(input, Room.class);

            room.setBeds(beds);

            roomRepository.save(room);
            log.info("Saved room {} in repository.", room);

            CreateRoomOutput result = CreateRoomOutput.builder()
                    .roomId(room.getId())
                    .build();

            log.info("End process method in CreateRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, BedNotFoundException.class),
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }

    private List<Bed> createBeds(CreateRoomInput input) {
        List<BedSize> bedSizes = new ArrayList<>();

        input.getBedSizes()
                .forEach(bedSize -> bedSizes.add(
                        BedSize.getBedSize(bedSize)
                        )
                );

        List<Bed> beds = new ArrayList<>();
        bedSizes.forEach(bedSize -> {
            Bed bed = bedRepository.findBedByBedSize(bedSize)
                    .orElseThrow(() -> {
                        log.warn("Bed with bed size {} not found.", bedSize);
                        return new BedNotFoundException();
                    });
            beds.add(bed);
        });

        return beds;
    }
}
