package com.tinqinacademy.hotel.api.base;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import io.vavr.control.Either;

public interface OperationProcessor<S extends OperationInput, T extends OperationOutput> {
    Either<ErrorOutput, T> process(S input);
}
