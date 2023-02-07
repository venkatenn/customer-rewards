package com.example.rewards.services.impl;

import com.example.rewards.exceptions.ResourceNotFoundException;
import com.example.rewards.models.Customer;
import com.example.rewards.models.Reward;
import com.example.rewards.models.Transaction;
import com.example.rewards.reporsitories.CustomerRepository;
import com.example.rewards.reporsitories.RewardRepository;
import com.example.rewards.reporsitories.TransactionRepository;
import com.example.rewards.services.TransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transaction service implementation.
 *
 * @author Venkat E
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
  @Autowired TransactionRepository transactionRepository;
  @Autowired CustomerRepository customerRepository;
  @Autowired RewardRepository rewardRepository;

  /**
   * @param transaction
   * @return
   */
  @Override
  public Transaction createTransaction(Transaction transaction) {
    // validate if the customer is valid before proceeding.
    Optional<Customer> customerDb =
        customerRepository.findByCustomerId(transaction.getCustomerId());
    if (customerDb.isEmpty()) {
      throw new ResourceNotFoundException(
          "Customer not found with Id=" + transaction.getCustomerId());
    }

    // First save transaction to generate a id.
    Transaction transactionDb = transactionRepository.save(transaction);

    // Generate Reward based on amount and save the Reward
    Reward reward =
        Reward.builder()
            .points(calculateRewaredAmount(transaction.getAmount()))
            .customerId(transaction.getCustomerId())
            .dateTime(transaction.getDatetime())
            .transactionId(transaction.getTransactionId())
            .build();
    rewardRepository.save(reward);
    return transactionDb;
  }

  /** @return */
  @Override
  public List<Transaction> getAllTransactions() {
    return transactionRepository.findAll();
  }

  @Override
  public Transaction getTransactionById(String transactionId) {
    Optional<Transaction> transactionDb = transactionRepository.findByTransactionId(transactionId);
    if (transactionDb.isPresent()) {
      return transactionDb.get();
    } else {
      throw new ResourceNotFoundException("Transaction not found with Id=" + transactionId);
    }
  }

  /**
   * @param transaction
   * @return
   */
  @Override
  public Transaction updateTransaction(Transaction transaction) {
    Optional<Transaction> transactionDb =
        transactionRepository.findByTransactionId(transaction.getTransactionId());
    if (transactionDb.isPresent()) {
      // Fist try to update reward and bail if we are not able to find corresponding reward
      Optional<Reward> rewardDb =
          rewardRepository.findByTransactionId(transaction.getTransactionId());
      if (rewardDb.isPresent()) {
        Reward rewardUpdate = rewardDb.get();
        rewardUpdate.setPoints(calculateRewaredAmount(transaction.getAmount()));
        rewardRepository.save(rewardUpdate);

        // Update trasaction
        Transaction transactionUpdate = transactionDb.get();
        transactionUpdate.setDatetime(transaction.getDatetime());
        transactionUpdate.setCustomerId(transaction.getCustomerId());
        transactionUpdate.setAmount(transaction.getAmount());
        transactionRepository.save(transactionUpdate);

        return transactionUpdate;
      } else {
        throw new ResourceNotFoundException("Trasaction reward not found to update");
      }
    } else {
      throw new ResourceNotFoundException(
          "Transaction not found with id=" + transaction.getTransactionId());
    }
  }

  /** @param transactionId */
  @Override
  public void deleteTransaction(String transactionId) {
    Optional<Transaction> transactionDb = transactionRepository.findByTransactionId(transactionId);
    if (transactionDb.isPresent()) {
      // First delete the corresponding reward points
      Optional<Reward> rewardDb = rewardRepository.findByTransactionId(transactionId);
      if (rewardDb.isPresent()) {
        rewardRepository.deleteByTransactionId(transactionId);
        transactionRepository.deleteByTransactionId(transactionId);
      } else {
        throw new ResourceNotFoundException("Reward point not found to delete=" + transactionId);
      }
    } else {
      throw new ResourceNotFoundException("Transaction not found with id=" + transactionId);
    }
  }

  /**
   * @param transactionAmount
   * @return
   */
  public BigDecimal calculateRewaredAmount(BigDecimal transactionAmount) {
    BigDecimal rewardAmount = BigDecimal.ZERO;
    BigDecimal rewardPoint = BigDecimal.ONE;
    BigDecimal rewardLevel1 = new BigDecimal(50);
    BigDecimal rewardLevel2 = new BigDecimal(100);
    if (transactionAmount != null && transactionAmount.compareTo(rewardLevel1) == 1) {
      rewardAmount =
          rewardAmount.add(transactionAmount.subtract(rewardLevel1).multiply(rewardPoint));
    }
    if (transactionAmount != null && transactionAmount.compareTo(rewardLevel2) == 1) {
      rewardAmount =
          rewardAmount.add(transactionAmount.subtract(rewardLevel2).multiply(rewardPoint));
    }
    return rewardAmount;
  }
}
