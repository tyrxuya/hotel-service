package com.tinqinacademy.hotel.persistence.models;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookingGuest {
    private UUID bookingId;
    private UUID guestId;
}
