package tech.eversoft.airlines.ordering.transaction.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.eversoft.airlines.ordering.transaction.domain.Transaction;
import tech.eversoft.airlines.ordering.transaction.domain.TransactionId;
import tech.eversoft.airlines.ordering.transaction.domain.TransactionRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class TransactionQueryController {
    private TransactionRepository transactionRepository;

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
    public Transaction get(@PathVariable("id") Long id) {
        return transactionRepository.findById(new TransactionId(id)).orElseThrow();
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }
}
