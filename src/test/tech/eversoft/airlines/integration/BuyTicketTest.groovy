package tech.eversoft.airlines.integration

import groovy.json.JsonOutput
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class BuyTicketTest extends Specification {
    @Autowired
    MockMvc mockMvc

    static def formatDate(LocalDateTime localDateTime) {
        localDateTime.format(DateTimeFormatter.ofPattern('yyyy-MM-dd'))
    }
    static def soonestThursday = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY))
    static def soonestMonday = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))

    static def currentId = 0

    def cleanup() {
        currentId++
    }

    def 'Successfully  buying a ticket to Africa of brand A, on Thursday, on a birthday'() {
        given:
        def dateOfBirth = formatDate(soonestThursday.minusYears(50))
        def flightId = 'XYZ123123'
        def basePrice = 27

        and:
        def expectedClientId = currentId
        def expectedOrderId = currentId
        def expectedTransactionId = currentId
        def expectedCalculationId = currentId
        def expectedDiscountedPrice = 20

        expect: 'add a client'
        mockMvc
                .perform(post('/client')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                birthDate: dateOfBirth
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.clientId.id').value(expectedClientId))

        and: 'add a flight'
        mockMvc
                .perform(post('/flight')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                flightId: flightId,
                                origin: 'wroclaw',
                                destination: "cairo",
                                timeHour: 10,
                                timeMinute: 20,
                                dayOfWeek: DayOfWeek.THURSDAY.getValue(),
                                dollarPrice: basePrice,
                                brand: "A"
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.flightId.id').value(flightId))

        and: 'place an order'
        mockMvc
                .perform(post('/order')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                clientId: expectedClientId,
                                flightId: flightId
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id.id').value(expectedOrderId))
                .andExpect(jsonPath('$.transactionId.id').value(expectedTransactionId))
                .andExpect(jsonPath('$.status').value('PendingPayment'))

        and: 'check transaction price'
        mockMvc
                .perform(get("/transaction/$expectedTransactionId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.price.amount').value(expectedDiscountedPrice))
                .andExpect(jsonPath('$.calculationId.id').value(expectedCalculationId))

        and: 'check discounts'
        mockMvc
                .perform(get("/calculation/$expectedCalculationId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.dollarPrice').value(expectedDiscountedPrice))
                .andExpect(jsonPath('$.discounts', Matchers.hasSize(2)))
                .andExpect(jsonPath('$.discounts', Matchers.hasItem('FlightParameterDiscount(destinationContinent=Africa, dayOfWeek=THURSDAY)')))
                .andExpect(jsonPath('$.discounts', Matchers.hasItem('BirthdayDiscount()')))
    }

    def 'Successfully buying a ticket to Africa of brand B, on Thursday'() {
        given:
        def dateOfBirth = formatDate(soonestThursday.minusYears(50).minusDays(2))
        def flightId = 'XYZ123123'
        def basePrice = 27

        and:
        def expectedClientId = currentId
        def expectedOrderId = currentId
        def expectedTransactionId = currentId
        def expectedCalculationId = currentId
        def expectedDiscountedPrice = 22

        expect: 'add a client'
        mockMvc
                .perform(post('/client')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                birthDate: dateOfBirth
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.clientId.id').value(expectedClientId))

        and: 'add a flight'
        mockMvc
                .perform(post('/flight')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                flightId: flightId,
                                origin: 'wroclaw',
                                destination: 'cairo',
                                timeHour: 10,
                                timeMinute: 20,
                                dayOfWeek: DayOfWeek.THURSDAY.getValue(),
                                dollarPrice: basePrice,
                                brand: "B"
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.flightId.id').value(flightId))

        and: 'place an order'
        mockMvc
                .perform(post('/order')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                clientId: expectedClientId,
                                flightId: flightId
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id.id').value(expectedOrderId))
                .andExpect(jsonPath('$.transactionId.id').value(expectedTransactionId))
                .andExpect(jsonPath('$.status').value('PendingPayment'))

        and: 'check transaction price'
        mockMvc
                .perform(get("/transaction/$expectedTransactionId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.price.amount').value(expectedDiscountedPrice))
                .andExpect(jsonPath('$.calculationId.id').value(expectedCalculationId))

        and: 'check no discounts were saved as this is brand B'
        mockMvc
                .perform(get("/calculation/$expectedCalculationId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.dollarPrice').value(expectedDiscountedPrice))
                .andExpect(jsonPath('$.discounts', Matchers.hasSize(0)))
    }

    def 'Successfully buying a ticket for an imported flight to Africa of brand A, on Monday, on a birthday'() {
        given:
        def dateOfBirth = formatDate(soonestMonday.minusYears(50))
        def flightId = 'XYZ123123'

        and:
        def expectedClientId = currentId
        def expectedOrderId = currentId
        def expectedTransactionId = currentId
        def expectedCalculationId = currentId
        def expectedDiscountedPrice = 95

        expect: 'add a client'
        mockMvc
                .perform(post('/client')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                birthDate: dateOfBirth
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.clientId.id').value(expectedClientId))

        and: 'add a flight'
        mockMvc
                .perform(post('/flight/import')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                url: 'https://raw.githubusercontent.com/PiotrSliwa/ddd-airline-tickets/master/exampleCreateFlightCommand.json'
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.flightId.id').value(flightId))
                .andExpect(jsonPath('$.schedule.dayOfWeek').value('MONDAY'))

        and: 'place an order'
        mockMvc
                .perform(post('/order')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson([
                                clientId: expectedClientId,
                                flightId: flightId
                        ])))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id.id').value(expectedOrderId))
                .andExpect(jsonPath('$.transactionId.id').value(expectedTransactionId))
                .andExpect(jsonPath('$.status').value('PendingPayment'))

        and: 'check transaction price'
        mockMvc
                .perform(get("/transaction/$expectedTransactionId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.price.amount').value(expectedDiscountedPrice))
                .andExpect(jsonPath('$.calculationId.id').value(expectedCalculationId))

        and: 'check discounts'
        mockMvc
                .perform(get("/calculation/$expectedCalculationId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.dollarPrice').value(expectedDiscountedPrice))
                .andExpect(jsonPath('$.discounts', Matchers.hasSize(1)))
                .andExpect(jsonPath('$.discounts', Matchers.hasItem('BirthdayDiscount()')))
    }
}

