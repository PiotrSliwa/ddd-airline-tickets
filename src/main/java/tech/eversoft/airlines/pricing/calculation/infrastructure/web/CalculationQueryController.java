package tech.eversoft.airlines.pricing.calculation.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationId;
import tech.eversoft.airlines.pricing.calculation.domain.CalculationRepository;

@RestController
@AllArgsConstructor
public class CalculationQueryController {
    private CalculationRepository calculationRepository;

    @RequestMapping(value = "/calculation/{id}", method = RequestMethod.GET)
    public CalculationDto get(@PathVariable("id") Long id) {
        var calculation = calculationRepository.getById(new CalculationId(id));
        return CalculationDto.of(calculation);
    }
}
