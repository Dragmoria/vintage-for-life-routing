package com.vintageforlife.client.validation;

import com.vintageforlife.client.enums.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {
    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext constraintValidatorContext) {
        if (role == null) {
            return true;
        }

        for (Role validRole : Role.values()) {
            if (validRole.equals(role)) {
                return true;
            }
        }

        return false;
    }
}