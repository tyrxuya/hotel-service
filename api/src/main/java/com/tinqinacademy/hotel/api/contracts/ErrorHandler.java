package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import org.springframework.validation.BindException;

public interface ErrorHandler {
    ErrorOutput handle(BindException ex);
}
