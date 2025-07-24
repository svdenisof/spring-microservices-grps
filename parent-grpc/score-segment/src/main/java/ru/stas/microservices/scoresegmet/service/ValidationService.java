package ru.stas.microservices.scoresegmet.service;

import com.google.protobuf.UInt64Value;
import org.springframework.stereotype.Service;
import ru.stas.grpc.scoresegment.ScoreSegmentErrorCode;
import ru.stas.microservices.scoresegmet.exception.ScoreSegmentException;
import ru.stas.microservices.utils.IdNumberUtils;

@Service
public class ValidationService {

    public final void validateIdNumber(final UInt64Value idNumber) {

        checkIfNull(idNumber);
        checkIfValid(idNumber);
    }

    private void checkIfValid(final UInt64Value idNumber) {
        if (idNumber == null) {
            throw new ScoreSegmentException(ScoreSegmentErrorCode.ID_NUMBER_CANNOT_BE_NULL);
        }
    }

    private void checkIfNull(final UInt64Value idNumber) {

        if (idNumber == null && !IdNumberUtils.isValid(idNumber.toString())) {
            throw new ScoreSegmentException(ScoreSegmentErrorCode.INVALID_ID_NUMBER_VALUE);
        }
    }
}
