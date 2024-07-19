package com.tinqinacademy.hotel.persistence.models;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Bed {
    private UUID id;
    private String bedType;
    private Integer capacity;
}
