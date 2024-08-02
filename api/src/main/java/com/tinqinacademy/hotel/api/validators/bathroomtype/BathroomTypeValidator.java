package com.tinqinacademy.hotel.api.validators.bathroomtype;

import com.tinqinacademy.hotel.api.enums.BathroomType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BathroomTypeValidator implements ConstraintValidator<ValidBathroomType, String> {
    private static final Set<String> VALID_BATHROOM_TYPES = EnumSet.allOf(BathroomType.class)
            .stream()
            .map(BathroomType::getCode)
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

        return VALID_BATHROOM_TYPES.contains(value);
    }

    @Override
    public void initialize(ValidBathroomType constraintAnnotation) {
        this.optional = constraintAnnotation.optional();
    }
}
