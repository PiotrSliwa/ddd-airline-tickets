package tech.eversoft.airlines.pricing.calculation.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import tech.eversoft.airlines.pricing.calculation.domain.Calculation;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationId;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CalculationRepositoryImpl implements CalculationRepository {
    private final List<Calculation> list = new ArrayList<>();

    @Override
    public Calculation save(Calculation calculation) {
        if (calculation.getCalculationId() != null) {
            list.set(calculation.getCalculationId().getId().intValue(), calculation);
            return calculation;
        }
        var id = new CalculationId((long) list.size());
        list.add(calculation);
        calculation.setCalculationId(id);
        return calculation;
    }

    @Override
    public Calculation getById(CalculationId calculationId) {
        return list.get(calculationId.getId().intValue());
    }

    @Override
    public List<Calculation> getAll() {
        return list;
    }
}
