package ru.stas.microservices.cityscore.exception;

import lombok.Getter;
import ru.stas.grpc.cityscore.CityScoreErrorCode;

import java.io.Serial;

@Getter
public final class CityScoreException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4734415372040205463L;

    private final CityScoreErrorCode errorCode;

    public CityScoreException(final CityScoreErrorCode thisErrorCode) {
        super(thisErrorCode.name());
        this.errorCode = thisErrorCode;
    }
}
