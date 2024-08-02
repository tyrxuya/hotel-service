package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorOutput;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GuestToHotelVisitorOutput extends AbstractConverter<Guest, HotelVisitorOutput> {
    @Override
    protected Class<HotelVisitorOutput> getTargetClass() {
        return HotelVisitorOutput.class;
    }

    @Override
    protected HotelVisitorOutput doConvert(Guest source) {
        HotelVisitorOutput result = HotelVisitorOutput.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNo(source.getPhone())
                .idNo(source.getCivilNumber())
                .idIssueAuthority(source.getIdIssueAuthority())
                .idIssueDate(source.getIdIssueDate())
                .build();

        return result;
    }
}
