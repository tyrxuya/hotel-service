package com.tinqinacademy.hotel.api.errors;

import com.tinqinacademy.hotel.api.exceptions.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Component
public class ErrorMapper {
    private final ErrorOutput errorOutput;
    private final View error;

    public <T extends Throwable> ErrorOutput map(T throwable, HttpStatus httpStatus) {
        errorOutput.setErrors(List.of(Errors.builder()
                .message(throwable.getMessage())
                .build()));

        errorOutput.setStatus(httpStatus);

        return errorOutput;
    }

//    public <T extends Throwable> ErrorOutput validatorMap(InvalidInputException ex, HttpStatus httpStatus) {
//        errorOutput.setErrors(ex.getErrorOutput().getErrors());
//        errorOutput.setStatus(httpStatus);
//        return errorOutput;
//    }
}
