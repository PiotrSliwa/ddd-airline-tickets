package tech.eversoft.airlines.flight.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import tech.eversoft.airlines.common.domain.DollarPrice;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

@Data
public class Schedule {
    @NonNull private final LocalTime localTime;
    @NonNull private final DayOfWeek dayOfWeek;

    //TODO Extract or rename the class
    @NonNull private final DollarPrice price;

    public LocalDateTime getDateTime(LocalDateTime since) {
        LocalDate adjustedDate = getAdjustedDateTime(since).toLocalDate();
        return adjustedDate.atTime(localTime);
    }

    private LocalDateTime getAdjustedDateTime(LocalDateTime since) {
        if (since.with(TemporalAdjusters.nextOrSame(dayOfWeek)).withHour(localTime.getHour()).withMinute(localTime.getMinute()).isBefore(since)) {
            return since.with(TemporalAdjusters.next(dayOfWeek));
        }
        return since.with(TemporalAdjusters.nextOrSame(dayOfWeek));
    }
}
