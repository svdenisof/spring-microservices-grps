package ru.stas.microservices.scorecalculator.model.validation.validator;

import org.springframework.beans.factory.annotation.Value;
import ru.stas.microservices.scorecalculator.model.validation.annotation.ValidName;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class NameValidator implements ConstraintValidator<ValidName, String> {

    @Value("${request.name.regex.pattern}")
    private String nameRegexPattern;

    private Pattern pattern;

    @Override
    public void initialize(final ValidName constraintAnnotation) {
        pattern = Pattern.compile(nameRegexPattern);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(value).matches();
    }
}
