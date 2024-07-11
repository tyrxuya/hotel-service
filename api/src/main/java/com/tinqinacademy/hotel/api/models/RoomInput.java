package com.tinqinacademy.hotel.api.models;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RoomInput {
    private String roomId;
    private String roomNumber;
    private Integer bedCount;
    private String bedSize;
    private Integer floor;
    private BigDecimal price;
    private String bathroomType;
}
