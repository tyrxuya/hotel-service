package com.tinqinacademy.hotel.api.operations.deleteroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.enums.BathroomType;
import com.tinqinacademy.hotel.api.enums.BedSize;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteRoomOutput implements OperationOutput {
    private String bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
    private List<String> bedSizes;
}
