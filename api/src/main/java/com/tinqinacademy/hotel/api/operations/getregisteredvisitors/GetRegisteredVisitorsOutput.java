package com.tinqinacademy.hotel.api.operations.getregisteredvisitors;

import com.tinqinacademy.hotel.api.contracts.base.OperationOutput;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRegisteredVisitorsOutput implements OperationOutput {
    private List<HotelVisitorOutput> hotelVisitors;
}
