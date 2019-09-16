package tech.eversoft.airlines.transaction.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import tech.eversoft.airlines.transaction.domain.Transaction;
import tech.eversoft.airlines.transaction.domain.TransactionId;
import tech.eversoft.airlines.transaction.domain.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private List<Transaction> list = new ArrayList<>();

    @Override
    synchronized public Transaction save(Transaction transaction) {
        if (transaction.getTransactionId() != null) {
            list.set(transaction.getTransactionId().getId().intValue(), transaction);
            return transaction;
        }
        var id = new TransactionId((long) list.size());
        list.add(transaction);
        transaction.setTransactionId(id);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(TransactionId id) {
        if (id.getId() >= list.size()) {
            return Optional.empty();
        }
        return Optional.of(list.get(id.getId().intValue()));
    }

    @Override
    public List<Transaction> findAll() {
        return list;
    }
}
