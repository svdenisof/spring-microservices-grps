package ru.stas.microservices.scorecalculator.service;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.UInt64Value;
import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stas.grpc.cityscore.CityScoreExceptionResponse;
import ru.stas.grpc.cityscore.CityScoreRequest;
import ru.stas.grpc.cityscore.CityScoreResponse;
import ru.stas.grpc.cityscore.CityScoreServiceGrpc;
import ru.stas.grpc.scoresegment.ScoreSegmentExceptionResponse;
import ru.stas.grpc.scoresegment.ScoreSegmentRequest;
import ru.stas.grpc.scoresegment.ScoreSegmentResponse;
import ru.stas.grpc.scoresegment.ScoreSegmentServiceGrpc;
import ru.stas.microservices.scorecalculator.domain.IncomeBracketMultiplierInfo;
import ru.stas.microservices.scorecalculator.exception.ScoreCalculatorException;
import ru.stas.microservices.scorecalculator.model.ScoreCalculatorErrorCode;
import ru.stas.microservices.scorecalculator.model.ScoreCalculatorRequest;

import java.math.BigInteger;
import java.util.Optional;

@Log4j2
@Service
public final class ScoreCalculatorService {

    @GrpcClient("city-score")
    private CityScoreServiceGrpc.CityScoreServiceBlockingStub cityScoreStub;

    @GrpcClient("score-segment")
    private ScoreSegmentServiceGrpc.ScoreSegmentServiceBlockingStub scoreSegmentStub;

    @Autowired
    private IncomeBracketMultiplierInfoService incomeBracketMultiplierInfoService;

    public BigInteger calculateScore(final ScoreCalculatorRequest request) {
        final IncomeBracketMultiplierInfo selectedIncomeBracketMultiplierInfo =
                getIncomeBracketMultiplerInfo(request.getIncomeBracketMultiplierId());

        final BigInteger scoreSegment = getScoreSegment(request.getIdNumber());
        final Integer cityScore = getCityScore(request.getCityCode());

        return BigInteger.valueOf(selectedIncomeBracketMultiplierInfo.getMultiplier())
                .multiply(scoreSegment).add(BigInteger.valueOf(cityScore));
    }

    private Integer getCityScore(final Integer cityCode) {
        final CityScoreRequest cityScoreRequest = CityScoreRequest
                                                        .newBuilder()
                                                        .setCityCode(cityCode)
                                                        .build();

        try {
            final CityScoreResponse cityScoreResponse = cityScoreStub.calculateCityScore(cityScoreRequest);

            return cityScoreResponse.getCityScore();
        } catch (StatusRuntimeException e) {
            final Status status = StatusProto.fromThrowable(e);
            for (final Any any : status.getDetailsList()) {
                if (!any.is(CityScoreExceptionResponse.class)) {
                    continue;
                }
                try {
                    final CityScoreExceptionResponse exceptionResponse = any.unpack(CityScoreExceptionResponse.class);
                    infoLogOnto(exceptionResponse.getTimestamp().toString(), exceptionResponse.getErrorCode().toString());
                } catch (InvalidProtocolBufferException ex) {
                    log.info(ex.getStackTrace());
                }
            }

        }

        return Integer.valueOf(1);
    }

    private BigInteger getScoreSegment(final BigInteger idNumber) {
        final ScoreSegmentRequest scoreSegmentRequest = ScoreSegmentRequest
                                        .newBuilder()
                                        .setIdNumber(UInt64Value.newBuilder().setValue(idNumber.longValue()).build())
                                        .build();

        try {
            final ScoreSegmentResponse scoreSegmentResponse = scoreSegmentStub.calculateScoreSegment(scoreSegmentRequest);

            return new BigInteger(scoreSegmentResponse.getScoreSegment().toString());
        } catch (Exception e) {
            final Status status = StatusProto.fromThrowable(e);
            for (final Any any : status.getDetailsList()) {
                if (!any.is(ScoreSegmentExceptionResponse.class)) {
                    continue;
                }
                try {
                    final ScoreSegmentExceptionResponse exceptionResponse = any.unpack(ScoreSegmentExceptionResponse.class);
                    infoLogOnto(exceptionResponse.getTimestamp().toString(), exceptionResponse.getErrorCode().toString());
                } catch (InvalidProtocolBufferException ex) {
                    log.info(ex.getStackTrace());
                }
            }
        }

        return BigInteger.ONE;
    }

    private IncomeBracketMultiplierInfo getIncomeBracketMultiplerInfo(final long incomeBracketMultiplierId) {
        final Optional<IncomeBracketMultiplierInfo> multiplierInfo =
                incomeBracketMultiplierInfoService.findById(incomeBracketMultiplierId);

        if (multiplierInfo.isEmpty()) {
            throw new ScoreCalculatorException(ScoreCalculatorErrorCode.INVALID_INCOME_BRACKET_MULTIPLIER_ID,
                    incomeBracketMultiplierId);
        }

        return multiplierInfo.get();
    }

    private void infoLogOnto(final String timestamp, final String errorCode) {
        log.info("timestamp: {}, errorCode : {}", timestamp, errorCode);
    }
}
