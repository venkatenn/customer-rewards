package com.example.rewards.reporsitories;

import com.example.rewards.models.Transaction;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongo repository to crud Transaction to mongodb.
 *
 * @author Venkat E
 */
public interface TransactionRepository extends MongoRepository<Transaction, Long> {

  /**
   * @param transactionId
   * @return
   */
  Optional<Transaction> findByTransactionId(String transactionId);

  /**
   * @param transactionId
   * @return
   */
  Optional<Transaction> deleteByTransactionId(String transactionId);
}
