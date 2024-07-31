package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.errors.Errors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class InvalidInputException extends RuntimeException {
    private final List<Errors> errors;
}
