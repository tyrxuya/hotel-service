package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.ExceptionMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
    private final String message = ExceptionMessages.USER_NOT_FOUND;
}
