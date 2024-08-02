package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetVisitorsInfo;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorOutput;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
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

@Service
@Slf4j
public class GetVisitorsInfoOperation extends BaseOperation implements GetVisitorsInfo {
    private final GuestRepository guestRepository;

    public GetVisitorsInfoOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, GuestRepository guestRepository) {
        super(validator, conversionService, errorMapper);
        this.guestRepository = guestRepository;
    }

    @Override
    public Either<ErrorOutput, GetRegisteredVisitorsOutput> process(GetRegisteredVisitorsInput input) {
        return Try.of(() -> {
            log.info("start getVisitorsInfo input: {}", input);

            validate(input);

            List<Guest> guests = searchGuestsFromRepository(input);
            List<HotelVisitorOutput> hotelVisitors = getHotelVisitorsFromGuests(guests);

            GetRegisteredVisitorsOutput result = GetRegisteredVisitorsOutput.builder()
                    .hotelVisitors(hotelVisitors)
                    .build();

            log.info("end getVisitorsInfo result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }

    private List<HotelVisitorOutput> getHotelVisitorsFromGuests(List<Guest> guests) {
        List<HotelVisitorOutput> hotelVisitors = new ArrayList<>();

        guests.forEach(guest -> hotelVisitors.add(conversionService.convert(guest, HotelVisitorOutput.class)));

        return hotelVisitors;
    }

    private List<Guest> searchGuestsFromRepository(GetRegisteredVisitorsInput input) {
        return guestRepository.searchGuests(input.getVisitor().getFirstName(),
                input.getVisitor().getLastName(),
                input.getVisitor().getPhoneNo(),
                input.getVisitor().getBirthday(),
                input.getVisitor().getCivilNumber(),
                input.getVisitor().getIdIssueAuthority(),
                input.getVisitor().getIdIssueDate(),
                input.getVisitor().getIdValidity());
    }
}
