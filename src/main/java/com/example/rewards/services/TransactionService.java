package com.example.rewards.services;

import com.example.rewards.models.Transaction;
import java.util.List;

/** @author Venkat E */
public interface TransactionService {
  Transaction createTransaction(Transaction transaction);

  List<Transaction> getAllTransactions();

  Transaction getTransactionById(String transactionId);

  Transaction updateTransaction(Transaction transaction);

  void deleteTransaction(String transactionId);
}
