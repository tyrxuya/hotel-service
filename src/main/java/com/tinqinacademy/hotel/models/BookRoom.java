package com.tinqinacademy.hotel.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookRoom {
    private BedSize bedSize;
    private Integer floor;
}
