package com.tinqinacademy.hotel.persistence.models;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Booking {
    private UUID id;
    private UUID roomId;
    private UUID userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
}
