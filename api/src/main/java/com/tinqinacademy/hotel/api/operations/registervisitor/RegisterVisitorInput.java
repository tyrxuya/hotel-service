package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RegisterVisitorInput {
    @Valid
    private List<HotelVisitorInput> hotelVisitors;
}