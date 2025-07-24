package ru.stas.microservices.scorecalculator.model.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ScoreCalculatorRequestValidationConstants {
    public static final String INVALID_ID_NUMBER_VALUE = "INVALID_ID_NUMBER_VALUE";
    public static final String INCOME_BRACKET_MULTIPLIER_ID_CANNOT_BE_NULL = "INCOME_BRACKET_MULTIPLIER_ID_CANNOT_BE_NULL";
    public static final String INVALID_INCOME_BRACKET_MULTIPLIER_ID = "INVALID_INCOME_BRACKET_MULTIPLIER_ID";
    public static final String INVALID_NAME_OR_SURNAME = "INVALID_NAME_OR_SURNAME";
    public static final String INVALID_PHONE_NUMBER = "INVALID_PHONE_NUMBER";
    public static final String INVALID_CITY_CODE_VALUE = "INVALID_CITY_CODE_VALUE";
}
