package com.vintageforlife.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SizeThatAllowsNullValidator implements ConstraintValidator<SizeThatAllowsNull, String> {

    private int min;
    private int max;

    @Override
    public void initialize(SizeThatAllowsNull constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int length = value.length();
        return length >= min && length <= max;
    }
}
