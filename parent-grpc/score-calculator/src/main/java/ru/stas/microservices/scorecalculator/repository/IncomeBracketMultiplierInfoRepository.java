package ru.stas.microservices.scorecalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stas.microservices.scorecalculator.domain.IncomeBracketMultiplierInfo;

public interface IncomeBracketMultiplierInfoRepository extends JpaRepository<IncomeBracketMultiplierInfo, Long> {
}
