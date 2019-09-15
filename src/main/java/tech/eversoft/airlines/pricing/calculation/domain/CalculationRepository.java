package tech.eversoft.airlines.pricing.calculation.domain;

import java.util.List;

public interface CalculationRepository {
    Calculation save(Calculation calculation);
    Calculation getById(CalculationId calculationId);
    List<Calculation> getAll();
}
