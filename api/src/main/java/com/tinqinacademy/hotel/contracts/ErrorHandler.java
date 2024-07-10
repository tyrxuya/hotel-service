package com.tinqinacademy.hotel.contracts;

import com.tinqinacademy.hotel.errors.ErrorOutput;
import org.springframework.validation.BindException;

public interface ErrorHandler {
    ErrorOutput handle(BindException ex);
}
