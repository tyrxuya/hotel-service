package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.ExceptionMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomNotFoundException extends RuntimeException {
    private final String message = ExceptionMessages.ROOM_NOT_FOUND;
}
