package ru.stas.microservices.scorecalculator.model.validation.validator;

import org.springframework.beans.factory.annotation.Value;
import ru.stas.microservices.scorecalculator.model.validation.annotation.ValidPhoneNumber;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Value("${request.phone-number.regex.pattern}")
    private String phoneNumberPattern;

    private Pattern pattern;

    @Override
    public void initialize(final ValidPhoneNumber constraintAnnotation) {
        pattern = Pattern.compile(phoneNumberPattern);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        // com.google.i18n.phonenumbers.PhoneNumberUtil lib could be used instead
        return pattern.matcher(value).matches();
    }
}
