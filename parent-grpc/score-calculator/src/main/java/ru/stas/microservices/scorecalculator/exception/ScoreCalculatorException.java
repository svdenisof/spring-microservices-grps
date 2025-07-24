package ru.stas.microservices.scorecalculator.exception;

import lombok.Getter;
import ru.stas.microservices.scorecalculator.model.ScoreCalculatorErrorCode;

import java.io.Serial;

@Getter
public class ScoreCalculatorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2078330867848218490L;

    private final ScoreCalculatorErrorCode errorCode;

    public ScoreCalculatorException(final ScoreCalculatorErrorCode theErrorCode, final Object... args) {
        this(theErrorCode, null, args);
    }

    public ScoreCalculatorException(final ScoreCalculatorErrorCode theErrorCode, final Throwable cause, final Object... args) {
        super(theErrorCode.name() + " - " + String.format(theErrorCode.getMessage(), args), cause);
        errorCode = theErrorCode;
    }
}
