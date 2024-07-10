package com.tinqinacademy.hotel.models;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RoomOutput {
    private String roomId;
    private String roomNumber;
    private Integer bedCount;
    private BedSize bedSize;
    private Integer floor;
    private BigDecimal price;
    private BathroomType bathroomType;
}
