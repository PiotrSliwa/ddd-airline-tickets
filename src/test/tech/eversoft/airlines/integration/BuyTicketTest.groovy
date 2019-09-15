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
    static def nextThursday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY))

    static def expectedId = 0

    def 'Successfully  buying a ticket to Africa of brand A, on Thursday, on a birthday'() {
        given:
        def dateOfBirth = formatDate(nextThursday.minusYears(50))
        def flightId = 'XYZ123123'
        def basePrice = 27

        and:
        def expectedClientId = 0
        def expectedOrderId = 0
        def expectedTransactionId = 0
        def expectedCalculationId = 0
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
        def dateOfBirth = formatDate(nextThursday.minusYears(50).minusDays(2))
        def flightId = 'XYZ123123'
        def basePrice = 27

        and:
        def expectedClientId = 1
        def expectedOrderId = 1
        def expectedTransactionId = 1
        def expectedCalculationId = 1
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
}

