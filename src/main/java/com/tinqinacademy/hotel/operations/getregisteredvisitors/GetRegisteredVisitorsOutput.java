package com.tinqinacademy.hotel.operations.getregisteredvisitors;

import com.tinqinacademy.hotel.operations.hotelvisitor.HotelVisitorOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRegisteredVisitorsOutput {
    private List<HotelVisitorOutput> hotelVisitors;
}
