package com.example.rewards.reporsitories;

import com.example.rewards.models.Transaction;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/** @author Venkat E */
public interface TransactionRepository extends MongoRepository<Transaction, Long> {
  Optional<Transaction> findByTransactionId(String transactionId);
  Optional<Transaction> deleteByTransactionId(String transactionId);
}
