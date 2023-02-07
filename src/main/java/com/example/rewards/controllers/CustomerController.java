package com.example.rewards.controllers;

import com.example.rewards.models.Customer;
import com.example.rewards.models.CustomerRewardsPerMonth;
import com.example.rewards.models.CustomerTotalRewardsReport;
import com.example.rewards.services.CustomerService;
import java.time.Month;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** @author Venkat E */
@RestController
@RequestMapping("customer")
public class CustomerController {

  @Autowired private CustomerService customerService;

  @GetMapping("all")
  public ResponseEntity<List<Customer>> getAllCustomers() {
    return ResponseEntity.ok().body(customerService.getAllCustomers());
  }

  @GetMapping("{customerId}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
    return ResponseEntity.ok().body(customerService.getCustomerById(customerId));
  }

  @GetMapping("")
  public ResponseEntity<Customer> getCustomerByFirstAndLastName(
      @RequestParam @NotBlank String firstName, @RequestParam @NotBlank String lastName) {
    return ResponseEntity.ok().body(customerService.getCustomerByName(firstName, lastName));
  }

  @PostMapping
  public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
    return ResponseEntity.ok().body(customerService.createCustomer(customer));
  }

  @PutMapping("{customerId}")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable String customerId, @Valid @RequestBody Customer customer) {
    customer.setCustomerId(customerId);
    return ResponseEntity.ok().body(customerService.updateCustomer(customer));
  }

  @DeleteMapping("{customerId}")
  public HttpStatus deleteCustomer(@PathVariable String customerId) {
    customerService.deleteCustomer(customerId);
    return HttpStatus.OK;
  }

  @GetMapping("total-rewards/{customerId}")
  public ResponseEntity<CustomerTotalRewardsReport> getCustomerTotalRewards(
      @PathVariable String customerId) {
    return ResponseEntity.ok().body(customerService.getCustomerTotalRewards(customerId));
  }

  @GetMapping("rewards-per-month")
  public ResponseEntity<CustomerRewardsPerMonth> getCustomerRewardsInAMonth(
      @RequestParam @NotBlank String customerId,
      @RequestParam @NotBlank Integer year,
      @RequestParam @NotBlank Month month) {
    return ResponseEntity.ok()
        .body(customerService.getCustomerRewardsForMonth(customerId, year, month));
  }
}
