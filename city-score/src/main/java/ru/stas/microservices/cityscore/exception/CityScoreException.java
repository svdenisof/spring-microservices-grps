package ru.stas.microservices.cityscore.exception;

import lombok.Getter;
import ru.stas.grpc.cityscore.CityScoreErrorCode;

import java.io.Serial;

@Getter
public class CityScoreException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4734415372040205463L;

    private final CityScoreErrorCode errorCode;

    public CityScoreException(CityScoreErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }
}
