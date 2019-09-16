package tech.eversoft.airlines.order.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tech.eversoft.airlines.client.domain.ClientId;
import tech.eversoft.airlines.flight.domain.FlightId;
import tech.eversoft.airlines.transaction.domain.TransactionCreated;
import tech.eversoft.airlines.transaction.domain.TransactionId;
import tech.eversoft.airlines.transaction.domain.TransactionPaid;
import tech.eversoft.airlines.transaction.domain.TransactionPaymentRejected;

@Data
@RequiredArgsConstructor
public class Order {

    enum Status {
        Created, PendingPayment, Confirmed, Rejected
    }

    private OrderId id;
    @NonNull private final ClientId clientId;
    @NonNull private final FlightId flightId;
    @NonNull private Status status = Status.Created;
    private TransactionId transactionId;

    public Order(OrderId id, @NonNull ClientId clientId, @NonNull FlightId flightId) {
        this.id = id;
        this.clientId = clientId;
        this.flightId = flightId;
    }

    public void handle(TransactionCreated transactionCreated) {
        transactionId = transactionCreated.getTransactionId();
        status = Status.PendingPayment;
    }

    public void handle(TransactionPaid transactionPaid) {
        status = Status.Confirmed;
    }

    public void handle(TransactionPaymentRejected transactionPaymentRejected) {
        status = Status.Rejected;
    }
}
