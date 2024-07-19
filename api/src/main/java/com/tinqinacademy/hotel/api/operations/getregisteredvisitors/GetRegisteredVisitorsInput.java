package com.tinqinacademy.hotel.api.operations.getregisteredvisitors;

import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRegisteredVisitorsInput {
    private HotelVisitorInput visitor;

    @Schema(example = "100b")
    private String roomNo;
}