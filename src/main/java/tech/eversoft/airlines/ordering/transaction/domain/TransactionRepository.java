package tech.eversoft.airlines.ordering.transaction.domain;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(TransactionId transactionId);
    List<Transaction> findAll();
}
