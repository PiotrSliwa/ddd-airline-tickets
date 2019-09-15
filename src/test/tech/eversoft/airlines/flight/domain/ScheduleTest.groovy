package tech.eversoft.airlines.flight.domain

import spock.lang.Specification
import tech.eversoft.airlines.common.domain.DollarPrice

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month

class ScheduleTest extends Specification {
    def "GetDateTime"() {
        given:
        def schedule = new Schedule(localTime, dayOfWeek, new DollarPrice(100L))

        expect:
        expected == schedule.getDateTime(since)

        where:
        since                                               | localTime            | dayOfWeek        || expected
        LocalDateTime.of(2019, Month.SEPTEMBER, 15, 11, 55) | LocalTime.of(19, 20) | DayOfWeek.SUNDAY || LocalDateTime.of(2019, Month.SEPTEMBER, 15, 19, 20)
        LocalDateTime.of(2019, Month.SEPTEMBER, 15, 11, 55) | LocalTime.of(5, 20)  | DayOfWeek.SUNDAY || LocalDateTime.of(2019, Month.SEPTEMBER, 22, 5, 20)
        LocalDateTime.of(2019, Month.SEPTEMBER, 15, 11, 55) | LocalTime.of(5, 20)  | DayOfWeek.MONDAY || LocalDateTime.of(2019, Month.SEPTEMBER, 16, 5, 20)
    }
}
