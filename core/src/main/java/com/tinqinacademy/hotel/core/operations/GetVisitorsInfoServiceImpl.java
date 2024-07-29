package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.GetVisitorsInfoService;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetVisitorsInfoServiceImpl implements GetVisitorsInfoService {
    private final GuestRepository guestRepository;
    private final ConversionService conversionService;

    @Override
    public GetRegisteredVisitorsOutput getVisitorsInfo(GetRegisteredVisitorsInput input) {
        log.info("start getVisitorsInfo input: {}", input);

        List<Guest> guests = guestRepository.searchGuests(input.getVisitor().getFirstName(),
                input.getVisitor().getLastName(),
                input.getVisitor().getPhoneNo(),
                input.getVisitor().getBirthday(),
                input.getVisitor().getCivilNumber(),
                input.getVisitor().getIdIssueAuthority(),
                input.getVisitor().getIdIssueDate(),
                input.getVisitor().getIdValidity());

        GetRegisteredVisitorsOutput result = GetRegisteredVisitorsOutput.builder()
                .hotelVisitors(conversionService.convert(guests, List.class))
                .build();

        log.info("end getVisitorsInfo result: {}", result);

        return result;
    }
}
