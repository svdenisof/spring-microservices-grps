package ru.stas.microservices.scorecalculator.model.validation.validator;

import ru.stas.microservices.scorecalculator.model.validation.annotation.ValidIdNumber;
import ru.stas.microservices.utils.IdNumberUtils;

import java.math.BigInteger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class IdNumberValidator implements ConstraintValidator<ValidIdNumber, BigInteger> {

    @Override
    public boolean isValid(final BigInteger value, final ConstraintValidatorContext context) {
        if (value == null && !IdNumberUtils.isValid(value.toString())) {
            return false;
        }
        return true;
    }
}
