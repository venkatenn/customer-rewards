package com.example.rewards.controllers;

import com.example.rewards.models.Customer;
import com.example.rewards.models.CustomerRewardsPerMonth;
import com.example.rewards.models.CustomerTotalRewardsReport;
import com.example.rewards.services.CustomerService;
import java.time.Month;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Controller which handles customer resource apis.
 *
 * @author Venkat E
 */
@RestController
@RequestMapping("customer")
public class CustomerController {
  Logger logger = LoggerFactory.getLogger(CustomerController.class);
  @Autowired private CustomerService customerService;

  /** @return */
  @GetMapping("all")
  public ResponseEntity<List<Customer>> getAllCustomers() {
    logger.debug("Getting all customers");
    return ResponseEntity.ok().body(customerService.getAllCustomers());
  }

  /**
   * @param customerId
   * @return
   */
  @GetMapping("{customerId}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
    logger.debug("Getting customer with id={}" + customerId);
    return ResponseEntity.ok().body(customerService.getCustomerById(customerId));
  }

  /**
   * @param firstName
   * @param lastName
   * @return
   */
  @GetMapping("")
  public ResponseEntity<Customer> getCustomerByFirstAndLastName(
      @RequestParam @NotBlank String firstName, @RequestParam @NotBlank String lastName) {
    logger.debug("Getting customer with firstName={} lastName={}", firstName, lastName);
    return ResponseEntity.ok().body(customerService.getCustomerByName(firstName, lastName));
  }

  /**
   * @param customer
   * @return
   */
  @PostMapping
  public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
    logger.debug("Creating customer={}", customer);
    return ResponseEntity.ok().body(customerService.createCustomer(customer));
  }

  /**
   * @param customerId
   * @param customer
   * @return
   */
  @PutMapping("{customerId}")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable String customerId, @Valid @RequestBody Customer customer) {
    logger.debug("Updatating customer={}", customer);
    customer.setCustomerId(customerId);
    return ResponseEntity.ok().body(customerService.updateCustomer(customer));
  }

  /**
   * @param customerId
   * @return
   */
  @DeleteMapping("{customerId}")
  public HttpStatus deleteCustomer(@PathVariable String customerId) {
    logger.debug("Delete cusotmer with id={}", customerId);
    customerService.deleteCustomer(customerId);
    return HttpStatus.OK;
  }

  /**
   * @param customerId
   * @return
   */
  @GetMapping("total-rewards/{customerId}")
  public ResponseEntity<CustomerTotalRewardsReport> getCustomerTotalRewards(
      @PathVariable String customerId) {
    logger.debug("Getting total rewards for custoerId={}", customerId);
    return ResponseEntity.ok().body(customerService.getCustomerTotalRewards(customerId));
  }

  /**
   * @param customerId
   * @param year
   * @param month
   * @return
   */
  @GetMapping("rewards-per-month")
  public ResponseEntity<CustomerRewardsPerMonth> getCustomerRewardsInAMonth(
      @RequestParam @NotBlank String customerId,
      @RequestParam @NotBlank Integer year,
      @RequestParam @NotBlank Month month) {
    logger.debug(
        "Getting monthly customer rewareds for customerId={}, year={}, month={}",
        customerId,
        year,
        month);
    return ResponseEntity.ok()
        .body(customerService.getCustomerRewardsForMonth(customerId, year, month));
  }
}
