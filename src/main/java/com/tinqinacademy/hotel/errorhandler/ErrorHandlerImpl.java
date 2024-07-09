package com.tinqinacademy.hotel.errorhandler;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorHandlerImpl implements ErrorHandler {
    @Override
    public ErrorOutput handle(BindException ex) {
        List<Error> errors = new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.add(
                                Error.builder()
                                        .message(error.getDefaultMessage())
                                        .field(error.getField())
                                        .errorCode(error.getCode())
                                        .build()
                        )
                );

        ErrorOutput errorOutput = ErrorOutput.builder()
                .errors(errors)
                .build();

        return errorOutput;
    }
}
