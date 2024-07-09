package com.tinqinacademy.hotel.errorhandler;

import org.springframework.validation.BindException;

public interface ErrorHandler {
    ErrorOutput handle(BindException ex);
}
