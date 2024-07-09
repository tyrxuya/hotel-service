package com.tinqinacademy.hotel.errorhandler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorHandlerImpl implements ErrorHandler {
    @Override
    public ErrorOutput handle(MethodArgumentNotValidException ex) {
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
