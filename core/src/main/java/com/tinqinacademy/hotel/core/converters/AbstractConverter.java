package com.tinqinacademy.hotel.core.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
public abstract class AbstractConverter<S, T> implements Converter<S, T> {
    @Override
    public T convert(S source) {
        log.info("Start convert from {} to {}",
                source.getClass().getSimpleName(),
                getTargetClass().getSimpleName());

        log.info("Source value: {}", source);

        T result = doConvert(source);

        log.info("End convert target value: {}", result);

        return result;
    }

    protected abstract Class<T> getTargetClass();

    protected abstract T doConvert(S source);
}
