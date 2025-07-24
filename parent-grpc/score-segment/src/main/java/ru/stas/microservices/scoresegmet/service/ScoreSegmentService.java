package ru.stas.microservices.scoresegmet.service;

import com.google.protobuf.UInt64Value;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.stas.grpc.scoresegment.ScoreSegmentRequest;
import ru.stas.grpc.scoresegment.ScoreSegmentResponse;
import ru.stas.grpc.scoresegment.ScoreSegmentServiceGrpc;

import java.math.BigInteger;

@GrpcService
public class ScoreSegmentService extends ScoreSegmentServiceGrpc.ScoreSegmentServiceImplBase {

    private final BigInteger idNumberMod = new BigInteger("9");

    @Autowired
    private ValidationService validationService;

    @Override
    public final void calculateScoreSegment(final ScoreSegmentRequest request,
                                            final StreamObserver<ScoreSegmentResponse> responseObserver) {

        validationService.validateIdNumber(request.getIdNumber());

        final BigInteger idNumber = new BigInteger(request.getIdNumber().toString());

        final BigInteger scoreSegment = compareScoreSegment(idNumber.mod(idNumberMod));

        final ScoreSegmentResponse response = ScoreSegmentResponse
                                        .newBuilder()
                                        .setScoreSegment(UInt64Value.newBuilder().setValue(scoreSegment.longValue()))
                                        .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

    private BigInteger compareScoreSegment(final BigInteger scoreSegment) {

        if (scoreSegment.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ONE;
        }

        return scoreSegment;
    }
}
