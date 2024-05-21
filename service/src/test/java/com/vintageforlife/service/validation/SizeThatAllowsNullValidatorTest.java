package com.vintageforlife.service.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SizeThatAllowsNullValidatorTest {
    private SizeThatAllowsNullValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new SizeThatAllowsNullValidator();
        SizeThatAllowsNull sizeThatAllowsNull = mock(SizeThatAllowsNull.class);
        context = mock(ConstraintValidatorContext.class);

        when(sizeThatAllowsNull.min()).thenReturn(5);
        when(sizeThatAllowsNull.max()).thenReturn(10);

        validator.initialize(sizeThatAllowsNull);
    }

    @Test
    public void testIsValidWithNullValue() {
        assertTrue(validator.isValid(null, context));
    }

    @Test
    public void testIsValidWithValueLengthLessThanMin() {
        assertFalse(validator.isValid("1234", context));
    }

    @Test
    public void testIsValidWithValueLengthMoreThanMax() {
        assertFalse(validator.isValid("12345678901", context));
    }

    @Test
    public void testIsValidWithValueLengthWithinRange() {
        assertTrue(validator.isValid("123456", context));
    }
}
