package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.contracts.base.OperationInput;
import com.tinqinacademy.hotel.api.errors.ErrorMapper;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.errors.Errors;
import com.tinqinacademy.hotel.api.exceptions.*;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.vavr.API.$;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class BaseOperation {
    protected final Validator validator;
    protected final ConversionService conversionService;
    protected final ErrorMapper errorMapper;

    public <T extends OperationInput> void validate(T input) {
        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<Errors> errors = new ArrayList<>();

            violations.forEach(violation ->
                errors.add(Errors.builder()
                                .message(violation.getMessage())
                                .field(violation.getPropertyPath().toString())
                                .build())
            );

            throw new InvalidInputException(errors);
        }
    }

    protected API.Match.Case<? extends Exception, ErrorOutput> customCase(Throwable cause,
                                                                          HttpStatus status,
                                                                          Class<? extends Exception> exceptionClass) {
        return API.Case($(instanceOf(exceptionClass)), errorMapper.map(cause, status));
    }

    protected API.Match.Case<? extends Exception, ErrorOutput> defaultCase(Throwable cause,
                                                                 HttpStatus status) {
        return API.Case($(), errorMapper.map(cause, status));
    }

    protected API.Match.Case<InvalidInputException, ErrorOutput> validateCase(Throwable cause,
                                                                              HttpStatus status) {
        InvalidInputException ex = (InvalidInputException) cause;

        return API.Case($(instanceOf(InvalidInputException.class)), () -> ErrorOutput.builder()
                .errors(ex.getErrors())
                .status(status)
                .build());
    }
}
