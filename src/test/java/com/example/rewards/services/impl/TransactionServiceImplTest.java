package com.example.rewards.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rewards.exceptions.ResourceNotFoundException;
import com.example.rewards.models.Customer;
import com.example.rewards.models.Reward;
import com.example.rewards.models.Transaction;
import com.example.rewards.reporsitories.CustomerRepository;
import com.example.rewards.reporsitories.RewardRepository;
import com.example.rewards.reporsitories.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** @author Venkat E */
class TransactionServiceImplTest {
  @Mock CustomerRepository customerRepository;
  @Mock RewardRepository rewardRepository;
  @Mock TransactionRepository transactionRepository;
  @InjectMocks TransactionServiceImpl transactionService;

  @BeforeEach
  public void before() {
    MockitoAnnotations.openMocks(this);
  }

  /** Test to create a transaction for a non existing customer. */
  @Test
  void createTransactionForNonExistingCustomer() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(10);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);

    Optional<Customer> customerOptional = Optional.empty();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    try {
      transactionService.createTransaction(transaction);
      fail("Expected ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Customer not found with Id=" + customerId, re.getMessage());
    }
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
  }

  /** Test to create a transaction */
  @Test
  void createTransaction() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);

    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);

    ArgumentCaptor<Transaction> transactionArgumentCaptor =
        ArgumentCaptor.forClass(Transaction.class);
    ArgumentCaptor<Reward> rewardArgumentCaptor = ArgumentCaptor.forClass(Reward.class);

    transactionService.createTransaction(transaction);
    verify(transactionRepository, times(1)).save(transactionArgumentCaptor.capture());
    verify(rewardRepository, times(1)).save(rewardArgumentCaptor.capture());
    Transaction transactionResult = transactionArgumentCaptor.getValue();
    Reward rewardResult = rewardArgumentCaptor.getValue();
    assertEquals(transaction, transactionResult);
    assertEquals(transactionId, rewardResult.getTransactionId());
    assertEquals(customerId, rewardResult.getCustomerId());
    assertEquals(dateTime, rewardResult.getDateTime());
    assertEquals(new BigDecimal(90), rewardResult.getPoints());
  }

  /** Test to get all transactions. */
  @Test
  void getAllTransactions() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    List<Transaction> transactionList = Arrays.asList(transaction, transaction);
    when(transactionRepository.findAll()).thenReturn(transactionList);
    List<Transaction> transactionsResult = transactionService.getAllTransactions();
    for (Transaction transactionResult : transactionsResult) {
      assertEquals(transaction, transactionResult);
    }
    verify(transactionRepository, times(1)).findAll();
  }

  /** Test to get transaction for a non existing id. */
  @Test
  void getTransactionByIdForNonExistingTransaction() {
    String transactionId = "transactionId";
    Optional<Transaction> transactionOptional = Optional.empty();
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);
    try {
      transactionService.getTransactionById(transactionId);
      fail("Excepted ResourceNotFoundException");
    } catch (ResourceNotFoundException re) {
      assertEquals("Transaction not found with Id=" + transactionId, re.getMessage());
    }
  }

  /** Test to get a transaction using Id. */
  @Test
  void getTransactionById() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    Optional<Transaction> transactionOptional = Optional.of(transaction);
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);
    Transaction transactionResult = transactionService.getTransactionById(transactionId);
    assertEquals(transaction, transactionResult);
    verify(transactionRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to update a transaction for non existing. */
  @Test
  void updateTransactionForNonExisting() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    Optional<Transaction> transactionOptional = Optional.empty();
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);
    try {
      transactionService.updateTransaction(transaction);
      fail("Excepted ResourceNotFoundException");
    } catch (ResourceNotFoundException re) {
      assertEquals("Transaction not found with id=" + transactionId, re.getMessage());
    }
    verify(transactionRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to update a transaction for non existing reward. */
  @Test
  void updateTransactionForNonExistingReward() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    Optional<Transaction> transactionOptional = Optional.of(transaction);
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);

    String rewardId = "reward-test-id";
    BigDecimal points = new BigDecimal(10);
    Reward reward = Reward.builder().build();
    reward.setRewardId(rewardId);
    reward.setCustomerId(customerId);
    reward.setPoints(points);
    reward.setTransactionId(transactionId);
    reward.setDateTime(dateTime);
    Optional<Reward> rewardOptional = Optional.empty();
    when(rewardRepository.findByTransactionId(transactionId)).thenReturn(rewardOptional);
    try {
      transactionService.updateTransaction(transaction);
      fail("Expected ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Trasaction reward not found to update", re.getMessage());
    }
    verify(transactionRepository, times(1)).findByTransactionId(isA(String.class));
    verify(rewardRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to update a transaction. */
  @Test
  void updateTransaction() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    Optional<Transaction> transactionOptional = Optional.of(transaction);
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);

    String rewardId = "reward-test-id";
    BigDecimal points = new BigDecimal(10);
    Reward reward = Reward.builder().build();
    reward.setRewardId(rewardId);
    reward.setCustomerId(customerId);
    reward.setPoints(points);
    reward.setTransactionId(transactionId);
    reward.setDateTime(dateTime);
    Optional<Reward> rewardOptional = Optional.of(reward);
    when(rewardRepository.findByTransactionId(transactionId)).thenReturn(rewardOptional);

    ArgumentCaptor<Transaction> transactionArgumentCaptor =
        ArgumentCaptor.forClass(Transaction.class);
    ArgumentCaptor<Reward> rewardArgumentCaptor = ArgumentCaptor.forClass(Reward.class);

    transactionService.updateTransaction(transaction);
    verify(transactionRepository, times(1)).findByTransactionId(isA(String.class));
    verify(rewardRepository, times(1)).findByTransactionId(isA(String.class));
    verify(transactionRepository, times(1)).save(transactionArgumentCaptor.capture());
    verify(rewardRepository, times(1)).save(rewardArgumentCaptor.capture());
    Transaction transactionResult = transactionArgumentCaptor.getValue();
    Reward rewardResult = rewardArgumentCaptor.getValue();
    assertEquals(transaction, transactionResult);
    assertEquals(transactionId, rewardResult.getTransactionId());
    assertEquals(customerId, rewardResult.getCustomerId());
    assertEquals(dateTime, rewardResult.getDateTime());
    assertEquals(new BigDecimal(90), rewardResult.getPoints());
  }

  /** Test to delete a transaction for non existing Id. */
  @Test
  void deleteTransactionForNonExistingId() {
    String transactionId = "transactionId";
    Optional<Transaction> transactionOptional = Optional.empty();
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);
    try {
      transactionService.deleteTransaction(transactionId);
      fail("Excepted ResourceNotFoundException");
    } catch (ResourceNotFoundException re) {
      assertEquals("Transaction not found with id=" + transactionId, re.getMessage());
    }
    verify(transactionRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to delete a transaction for non existing reward. */
  @Test
  void deleteTransactionForNonExistingReward() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    Optional<Transaction> transactionOptional = Optional.of(transaction);
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);

    String rewardId = "reward-test-id";
    Optional<Reward> rewardOptional = Optional.empty();
    when(rewardRepository.findByTransactionId(transactionId)).thenReturn(rewardOptional);
    try {
      transactionService.deleteTransaction(transactionId);
      fail("Expected ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Reward point not found to delete=" + transactionId, re.getMessage());
    }
    verify(transactionRepository, times(1)).findByTransactionId(isA(String.class));
    verify(rewardRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to delete a transaction. */
  @Test
  void deleteTransaction() {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    Optional<Transaction> transactionOptional = Optional.of(transaction);
    when(transactionRepository.findByTransactionId(transactionId)).thenReturn(transactionOptional);

    String rewardId = "reward-test-id";
    BigDecimal points = new BigDecimal(10);
    Reward reward = Reward.builder().build();
    reward.setRewardId(rewardId);
    reward.setCustomerId(customerId);
    reward.setPoints(points);
    reward.setTransactionId(transactionId);
    reward.setDateTime(dateTime);
    Optional<Reward> rewardOptional = Optional.of(reward);
    when(rewardRepository.findByTransactionId(transactionId)).thenReturn(rewardOptional);

    transactionService.deleteTransaction(transactionId);
    verify(transactionRepository, times(1)).findByTransactionId(isA(String.class));
    verify(rewardRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to calculate rewards points based on transaction amounts. */
  @Test
  void calculateRewaredAmount() {
    assertEquals(new BigDecimal(0), transactionService.calculateRewaredAmount(null));
    assertEquals(new BigDecimal(0), transactionService.calculateRewaredAmount(new BigDecimal(0)));
    assertEquals(new BigDecimal(25), transactionService.calculateRewaredAmount(new BigDecimal(75)));
    assertEquals(
        new BigDecimal(90), transactionService.calculateRewaredAmount(new BigDecimal(120)));
  }
}
