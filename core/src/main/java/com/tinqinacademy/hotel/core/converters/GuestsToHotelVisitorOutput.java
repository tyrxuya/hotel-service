package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorOutput;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GuestsToHotelVisitorOutput extends AbstractConverter<List<Guest>, List> {
    @Override
    protected Class<List> getTargetClass() {
        return List.class;
    }

    @Override
    protected List doConvert(List<Guest> source) {
        List<HotelVisitorOutput> result = new ArrayList<>();

        source.forEach(guest -> result.add(HotelVisitorOutput.builder()
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .phoneNo(guest.getPhone())
                .idNo(guest.getCivilNumber())
                .idIssueAuthority(guest.getIdIssueAuthority())
                .idIssueDate(guest.getIdIssueDate())
                .build())
        );

        return result;
    }
}
