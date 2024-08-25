package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.RoomNotFoundException;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomAvailability;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CheckRoomAvailabilityOperation extends BaseOperation implements CheckRoomAvailability {
    private final BookingRepository bookingRepository;

    public CheckRoomAvailabilityOperation(Validator validator,
                                          ConversionService conversionService,
                                          ErrorMapper errorMapper,
                                          BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, CheckRoomsOutput> process(CheckRoomsInput input) {
        return Try.of(() -> {
            log.info("Start process method in CheckRoomAvailabilityOperation. Input: {}", input);

            validate(input);

            List<String> roomIds = getRoomIds(input);
            log.info("Room ids that match the input: {}", roomIds);

            CheckRoomsOutput result = CheckRoomsOutput.builder()
                    .idList(roomIds)
                    .build();

            log.info("End process method in BookRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        customCase(throwable, HttpStatus.NOT_FOUND, RoomNotFoundException.class)
                ));
    }

    private List<String> getRoomIds(CheckRoomsInput input) {
        List<String> roomIds = new ArrayList<>();
        bookingRepository.findBookingsByStartDateAndEndDateAndBedSizeAndBathroomTypeAndBedCount(input.getStartDate(),
                        input.getEndDate(),
                        BedSize.getBedSize(input.getBedSize()),
                        BathroomType.getBathroomType(input.getBathroomType()),
                        input.getBedCount())
                .forEach(booking -> roomIds.add(booking.getRoom()
                        .getId()
                        .toString())
                );

        if (roomIds.isEmpty()) {
            throw new RoomNotFoundException();
        }
        return roomIds;
    }
}
