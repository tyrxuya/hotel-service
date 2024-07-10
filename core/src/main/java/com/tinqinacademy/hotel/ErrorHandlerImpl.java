package com.tinqinacademy.hotel;

import com.tinqinacademy.hotel.contracts.ErrorHandler;
import com.tinqinacademy.hotel.errors.Error;
import com.tinqinacademy.hotel.errors.ErrorOutput;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorHandlerImpl implements ErrorHandler {
    @Override
    public ErrorOutput handle(BindException ex) {
        List<Error> errors = new ArrayList<>();

//        Arrays.stream(ex.getSuppressed())
//                .forEach(error -> errors.add(
//                        Error.builder()
//                                .message(error.getLocalizedMessage())
//                                .build()
//                        )
//                );

//        Set<ConstraintViolation<Object>> constraintViolations = validator.validate();
//
        //BindException e = new BindException(ex, "ex");

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

    private ErrorOutput handleEx(ConstraintViolationException ex) {
        System.out.println();
        return null;
    }
}
