package com.tinqinacademy.hotel.api.validators.bedsize;

import com.tinqinacademy.hotel.api.enums.BedSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BedSizeValidator implements ConstraintValidator<ValidBedSize, String> {
    private static final Set<String> VALID_BED_SIZES = EnumSet.allOf(BedSize.class)
            .stream()
            .map(BedSize::getCode)
            .collect(Collectors.toSet());

    private boolean optional;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (optional && (value == null || value.isEmpty())) {
            return true;
        }

        if (value == null || value.isEmpty()) {
            return false;
        }

        return VALID_BED_SIZES.contains(value);
    }

    @Override
    public void initialize(ValidBedSize constraintAnnotation) {
        this.optional = constraintAnnotation.optional();
    }
}
