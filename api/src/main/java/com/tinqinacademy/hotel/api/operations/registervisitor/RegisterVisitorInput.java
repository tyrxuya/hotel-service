package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RegisterVisitorInput implements OperationInput {
    @JsonIgnore
    @UUID(message = "bookingId must be a valid UUID")
    private String bookingId;

    private List<@Valid HotelVisitorInput> hotelVisitors;
}
