package ru.stas.microservices.cityscore.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.stas.grpc.cityscore.CityScoreRequest;
import ru.stas.grpc.cityscore.CityScoreResponse;
import ru.stas.grpc.cityscore.CityScoreServiceGrpc;

@GrpcService
public class CityScoreService extends CityScoreServiceGrpc.CityScoreServiceImplBase {

    @Autowired
    private ValidationService validationService;

    @Override
    public void calculateCityScore(final CityScoreRequest request, final StreamObserver<CityScoreResponse> responseObserver) {

        validationService.validationCityCode(request.getCityCode());

        int cityScore = request.getCityCode() * 10;

        CityScoreResponse response = CityScoreResponse.newBuilder().setCityScore(cityScore).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
