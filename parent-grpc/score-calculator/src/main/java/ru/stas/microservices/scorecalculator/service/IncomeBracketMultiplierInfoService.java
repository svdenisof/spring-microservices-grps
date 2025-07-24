package ru.stas.microservices.scorecalculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stas.microservices.scorecalculator.domain.IncomeBracketMultiplierInfo;
import ru.stas.microservices.scorecalculator.repository.IncomeBracketMultiplierInfoRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class IncomeBracketMultiplierInfoService {

    private IncomeBracketMultiplierInfoRepository incomeBracketMultiplierInfoRepository;

    public Optional<IncomeBracketMultiplierInfo> findById(final long id) {
        return incomeBracketMultiplierInfoRepository.findById(id);
    }
}
