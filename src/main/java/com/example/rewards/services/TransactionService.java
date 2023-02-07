package com.example.rewards.services;

import com.example.rewards.models.Transaction;
import java.util.List;

/**
 * Transaction Service Interface
 *
 * @author Venkat E
 */
public interface TransactionService {

  /**
   * @param transaction
   * @return
   */
  Transaction createTransaction(Transaction transaction);

  /** @return */
  List<Transaction> getAllTransactions();

  /**
   * @param transactionId
   * @return
   */
  Transaction getTransactionById(String transactionId);

  /**
   * @param transaction
   * @return
   */
  Transaction updateTransaction(Transaction transaction);

  /** @param transactionId */
  void deleteTransaction(String transactionId);
}
