package com.example.rewards.controllers;

import com.example.rewards.models.Transaction;
import com.example.rewards.services.TransactionService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller which handles Transaction api.
 *
 * @author Venkat E
 */
@RestController
@RequestMapping("transaction")
public class TransactionController {

  @Autowired TransactionService transactionService;

  /** @return */
  @GetMapping("all")
  public ResponseEntity<List<Transaction>> getAllTransactions() {
    return ResponseEntity.ok().body(transactionService.getAllTransactions());
  }

  /**
   * @param transactionId
   * @return
   */
  @GetMapping("{transactionId}")
  public ResponseEntity<Transaction> getTransactionById(@PathVariable String transactionId) {
    return ResponseEntity.ok().body(transactionService.getTransactionById(transactionId));
  }

  /**
   * @param transaction
   * @return
   */
  @PostMapping
  public ResponseEntity<Transaction> createTransaction(
      @Valid @RequestBody Transaction transaction) {
    return ResponseEntity.ok().body(transactionService.createTransaction(transaction));
  }

  /**
   * @param transactionId
   * @param transaction
   * @return
   */
  @PutMapping("{transactionId}")
  public ResponseEntity<Transaction> updateTransaction(
      @PathVariable String transactionId, @Valid @RequestBody Transaction transaction) {
    transaction.setTransactionId(transactionId);
    return ResponseEntity.ok().body(transactionService.updateTransaction(transaction));
  }

  /**
   * @param transactionId
   * @return
   */
  @DeleteMapping("{transactionId}")
  public HttpStatus deleteTransaction(@PathVariable String transactionId) {
    transactionService.deleteTransaction(transactionId);
    return HttpStatus.OK;
  }
}
