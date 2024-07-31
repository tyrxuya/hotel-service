package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.ExceptionMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingNotFoundException extends RuntimeException {
    private final String message = ExceptionMessages.BOOKING_NOT_FOUND;
}
