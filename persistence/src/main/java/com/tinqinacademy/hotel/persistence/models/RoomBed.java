package com.tinqinacademy.hotel.persistence.models;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RoomBed {
    private UUID roomId;
    private UUID bedId;
}
