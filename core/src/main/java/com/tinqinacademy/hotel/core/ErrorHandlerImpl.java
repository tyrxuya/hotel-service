package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.contracts.ErrorHandler;
import com.tinqinacademy.hotel.api.errors.Errors;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorHandlerImpl implements ErrorHandler {
    @Override
    public ErrorOutput handle(Exception ex) {
        List<Errors> errors = new ArrayList<>();

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

        if (ex instanceof BindException bindException) {
            bindException.getBindingResult()
                    .getFieldErrors()
                    .forEach(error -> errors.add(
                                    Errors.builder()
                                            .message(error.getDefaultMessage())
                                            .field(error.getField())
                                            .build()
                            )
                    );
        }
        else if (ex instanceof ConstraintViolationException constraintException) {
            constraintException.getConstraintViolations()
                    .forEach(error -> errors.add(
                            Errors.builder()
                                    .message(error.getMessage())
                                    .build()
                            )
                    );
        }
        else {
            errors.add(Errors.builder()
                    .message(ex.getMessage())
                    .build());
        }

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
