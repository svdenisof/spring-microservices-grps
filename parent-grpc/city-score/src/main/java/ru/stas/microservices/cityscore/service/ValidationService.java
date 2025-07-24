package ru.stas.microservices.cityscore.service;

import org.springframework.stereotype.Service;
import ru.stas.grpc.cityscore.CityScoreErrorCode;
import ru.stas.microservices.cityscore.exception.CityScoreException;

@Service
final class ValidationService {

    public void validationCityCode(final Integer cityCode) {
        checkIfNull(cityCode);
        checkIfValid(cityCode);
    }

    private void checkIfNull(final Integer cityCode) {
        if (cityCode == null) {
            throw new CityScoreException(CityScoreErrorCode.CITY_CODE_CANNOT_BE_NULL);
        }
    }

    private void checkIfValid(final Integer cityCode) {
        if (cityCode < 1 || cityCode > 81) {
            throw new CityScoreException(CityScoreErrorCode.INVALID_CITY_CODE_VALUE);
        }
    }
}
