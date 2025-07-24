package ru.stas.microservices.cityscore.exception;

import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;
import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import ru.stas.grpc.cityscore.CityScoreExceptionResponse;

import java.time.Instant;

@GrpcAdvice
public final class CityScoreExceptionHandler {

    @GrpcExceptionHandler(CityScoreException.class)
    public StatusRuntimeException handleValidationError(final CityScoreException cause) {

        final Instant time = Instant.now();
        final Timestamp timestamp = Timestamp
                                        .newBuilder()
                                        .setSeconds(time.getEpochSecond())
                                        .setNanos(time.getNano())
                                        .build();

        final CityScoreExceptionResponse exceptionResponse = CityScoreExceptionResponse
                                                                    .newBuilder()
                                                                    .setErrorCode(cause.getErrorCode())
                                                                    .setTimestamp(timestamp)
                                                                    .build();

        final Status status = Status
                .newBuilder()
                .setCode(Code.INVALID_ARGUMENT.getNumber())
                .setMessage("Invalid city code")
                .addDetails(Any.pack(exceptionResponse))
                .build();

        return StatusProto.toStatusRuntimeException(status);
    }
}
