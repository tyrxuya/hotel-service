package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RegisterVisitorInput {
    @JsonIgnore
    private String bookingId;

    @Valid
    private List<HotelVisitorInput> hotelVisitors;
}
