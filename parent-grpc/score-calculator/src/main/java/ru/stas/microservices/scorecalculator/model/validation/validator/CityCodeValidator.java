package ru.stas.microservices.scorecalculator.model.validation.validator;

import ru.stas.microservices.scorecalculator.model.validation.annotation.ValidCityCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class CityCodeValidator implements ConstraintValidator<ValidCityCode, Integer> {

    @Override
    public boolean isValid(final Integer integer, final ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
