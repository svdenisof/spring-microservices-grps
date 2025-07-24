package ru.stas.microservices.scorecalculator.model.validation.annotation;

import ru.stas.microservices.scorecalculator.model.validation.ScoreCalculatorRequestValidationConstants;
import ru.stas.microservices.scorecalculator.model.validation.validator.PhoneNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {

    String message() default ScoreCalculatorRequestValidationConstants.INVALID_PHONE_NUMBER;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
