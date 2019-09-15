package tech.eversoft.airlines.common.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Data
public class DollarPrice {
    //TODO BigDecimal
    @NonNull private final Long amount;
}
