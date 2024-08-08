package com.tinqinacademy.hotel.api.operations.getregisteredvisitors;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRegisteredVisitorsInput implements OperationInput {
    @Valid
    private HotelVisitorInput visitor;

    @Schema(example = "100b")
    private String roomNo;
}
