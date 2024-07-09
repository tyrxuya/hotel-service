package com.tinqinacademy.hotel.errorhandler;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorHandler {
    ErrorOutput handle(BindException ex);
}
