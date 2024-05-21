package com.vintageforlife.service.validation;

import com.vintageforlife.service.enums.Role;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class RoleValidatorTest {
    private RoleValidator roleValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        roleValidator = new RoleValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testIsValidWithNullRole() {
        assertTrue(roleValidator.isValid(null, context));
    }

    @Test
    public void testIsValidWithValidRoles() {
        for (Role role : Role.values()) {
            assertTrue(roleValidator.isValid(role, context));
        }
    }
}
