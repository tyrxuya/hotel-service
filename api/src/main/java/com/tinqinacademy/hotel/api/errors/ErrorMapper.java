package com.tinqinacademy.hotel.api.errors;

import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
@Component
public class ErrorMapper {
    private final ErrorOutput errorOutput;

    public <T extends Throwable> ErrorOutput map(T throwable, HttpStatus httpStatus) {
        log.info("Mapping throwable {} to ErrorOutput", throwable.getClass().getName());

        errorOutput.setErrors(List.of(Errors.builder()
                .message(throwable.getMessage())
                .build()));

        errorOutput.setStatus(httpStatus);

        log.info("Result: {}", errorOutput);

        return errorOutput;
    }

//    public <T extends Throwable> ErrorOutput validatorMap(InvalidInputException ex, HttpStatus httpStatus) {
//        errorOutput.setErrors(ex.getErrorOutput().getErrors());
//        errorOutput.setStatus(httpStatus);
//        return errorOutput;
//    }
}
