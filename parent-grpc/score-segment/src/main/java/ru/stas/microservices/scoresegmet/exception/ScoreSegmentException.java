package ru.stas.microservices.scoresegmet.exception;

import lombok.Getter;
import ru.stas.grpc.scoresegment.ScoreSegmentErrorCode;

import java.io.Serial;

@Getter
public final class ScoreSegmentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 9204546817788524299L;

    private final ScoreSegmentErrorCode errorCode;

    public ScoreSegmentException(final ScoreSegmentErrorCode theErrorCode) {
        super(theErrorCode.name());
        errorCode = theErrorCode;
    }
}
