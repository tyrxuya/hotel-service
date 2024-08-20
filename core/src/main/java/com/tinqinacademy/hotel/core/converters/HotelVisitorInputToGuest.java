package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import org.springframework.stereotype.Component;

@Component
public class HotelVisitorInputToGuest extends AbstractConverter<HotelVisitorInput, Guest> {
    @Override
    protected Class<Guest> getTargetClass() {
        return Guest.class;
    }

    @Override
    protected Guest doConvert(HotelVisitorInput source) {
        Guest result = Guest.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phone(source.getPhoneNo())
                .birthday(source.getBirthday())
                .civilNumber(source.getCivilNumber())
                .idIssueAuthority(source.getIdIssueAuthority())
                .idIssueDate(source.getIdIssueDate())
                .idValidity(source.getIdValidity())
                .build();

        return result;
    }
}
