package com.example.rewards.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.rewards.models.Transaction;
import com.example.rewards.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** @author Venkat E */
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
  @MockBean TransactionService transactionService;
  @Autowired private MockMvc mockMvc;

  final ObjectMapper MAPPER = new ObjectMapper();
  ObjectWriter WRITER;

  @BeforeEach
  public void before() {
    MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    MAPPER.registerModule(new JavaTimeModule());
    WRITER = MAPPER.writer().withDefaultPrettyPrinter();
  }

  /**
   * Test to get all transactions api.
   *
   * @throws Exception
   */
  @Test
  void getAllTransactions() throws Exception {
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
    when(transactionService.getAllTransactions()).thenReturn(transactionList);

    mockMvc
        .perform(get("/transaction/all"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(equalTo(2))));
    verify(transactionService, times(1)).getAllTransactions();
  }

  /**
   * Test to get transaction usign id api.
   *
   * @throws Exception
   */
  @Test
  void getTransactionById() throws Exception {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    when(transactionService.getTransactionById(transactionId)).thenReturn(transaction);

    mockMvc
        .perform(get("/transaction/{transactionId}", transactionId))
        .andDo(print())
        .andExpect(status().isOk());
    verify(transactionService, times(1)).getTransactionById(isA(String.class));
  }

  /**
   * Test to create a transaction api.
   *
   * @throws Exception
   */
  @Test
  void createTransaction() throws Exception {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    String transactionJson = WRITER.writeValueAsString(transaction);

    mockMvc
        .perform(
            post("/transaction").contentType(MediaType.APPLICATION_JSON).content(transactionJson))
        .andDo(print())
        .andExpect(status().isOk());
    verify(transactionService, times(1)).createTransaction(isA(Transaction.class));
  }

  /**
   * Test to update a transaction api.
   *
   * @throws Exception
   */
  @Test
  void updateTransaction() throws Exception {
    String transactionId = "transactionId";
    String customerId = "customerId";
    BigDecimal amount = new BigDecimal(120);
    LocalDateTime dateTime = LocalDateTime.now();
    Transaction transaction = Transaction.builder().build();
    transaction.setTransactionId(transactionId);
    transaction.setCustomerId(customerId);
    transaction.setAmount(amount);
    transaction.setDatetime(dateTime);
    String transactionJson = WRITER.writeValueAsString(transaction);

    mockMvc
        .perform(
            put("/transaction/{transactionId}", transactionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionJson))
        .andDo(print())
        .andExpect(status().isOk());
    verify(transactionService, times(1)).updateTransaction(isA(Transaction.class));
  }

  /**
   * Test to delete a transaction api.
   *
   * @throws Exception
   */
  @Test
  void deleteTransaction() throws Exception {
    String transactionId = "transactionId";
    mockMvc
        .perform(delete("/transaction/{transactionId}", transactionId))
        .andDo(print())
        .andExpect(status().isOk());
    verify(transactionService, times(1)).deleteTransaction(isA(String.class));
  }
}
