package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
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

    public CheckRoomAvailabilityOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, BookingRepository bookingRepository) {
        super(validator, conversionService, errorMapper);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorOutput, CheckRoomsOutput> process(CheckRoomsInput input) {
        return Try.of(() -> {
            log.info("start checkRoomAvailability input: {}", input);

            validate(input);

            List<UUID> roomIds = getRoomIds(input);

            CheckRoomsOutput result = CheckRoomsOutput.builder()
                    .idList(roomIds)
                    .build();

            log.info("end checkRoomAvailability result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }

    private List<UUID> getRoomIds(CheckRoomsInput input) {
        List<UUID> roomIds = new ArrayList<>();
        bookingRepository.findBookingsByStartDateAndEndDateAndBedSizeAndBathroomTypeAndBedCount(input.getStartDate(),
                        input.getEndDate(),
                        BedSize.getBedSize(input.getBedSize()),
                        BathroomType.getBathroomType(input.getBathroomType()),
                        input.getBedCount())
                .forEach(booking -> roomIds.add(booking.getRoom().getId()));
        return roomIds;
    }
}
