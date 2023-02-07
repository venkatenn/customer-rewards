package com.example.rewards.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rewards.exceptions.ResourceNotFoundException;
import com.example.rewards.models.Customer;
import com.example.rewards.models.CustomerRewardsPerMonth;
import com.example.rewards.models.CustomerTotalRewardsReport;
import com.example.rewards.models.Reward;
import com.example.rewards.reporsitories.CustomerRepository;
import com.example.rewards.reporsitories.RewardRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** @author Venkat E */
class CustomerServiceImplTest {
  @Mock CustomerRepository customerRepository;
  @Mock RewardRepository rewardRepository;
  @InjectMocks CustomerServiceImpl customerService;

  @BeforeEach
  public void before() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createCustomer() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    when(customerRepository.save(customer)).thenReturn(customer);
    Customer customerResult = customerService.createCustomer(customer);
    assertEquals(customer, customerResult);
    verify(customerRepository, times(1)).save(isA(Customer.class));
  }

  @Test
  void updateCustomerCase1() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    Customer customerResult = customerService.updateCustomer(customer);
    assertEquals(customer, customerResult);
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
    verify(customerRepository, times(1)).save(isA(Customer.class));
  }

  @Test
  void updateCustomerCase2() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.empty();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    try {
      customerService.updateCustomer(customer);
      fail("Expected a ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Customer not found with Id=" + customer.getCustomerId(), re.getMessage());
    }
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getAllCustomers() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    List<Customer> customers = Arrays.asList(customer, customer);
    when(customerRepository.findAll()).thenReturn(customers);
    List<Customer> customersResult = customerService.getAllCustomers();
    for (Customer customerResult : customersResult) {
      assertEquals(customer, customerResult);
    }
    verify(customerRepository, times(1)).findAll();
  }

  @Test
  void getCustomerByIdCase1() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    Customer customerResult = customerService.getCustomerById(customerId);
    assertEquals(customer, customerResult);
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getCustomerByIdCase2() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.empty();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    try {
      customerService.getCustomerById(customerId);
      fail("Expected a ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Customer not found with Id=" + customerId, re.getMessage());
    }
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getCustomerByNameCase1() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByFirstNameAndLastName(firstName, lastName))
        .thenReturn(customerOptional);
    Customer customerResult = customerService.getCustomerByName(firstName, lastName);
    assertEquals(customer, customerResult);
    verify(customerRepository, times(1))
        .findByFirstNameAndLastName(isA(String.class), isA(String.class));
  }

  @Test
  void getCustomerByNameCase2() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.empty();
    when(customerRepository.findByFirstNameAndLastName(firstName, lastName))
        .thenReturn(customerOptional);
    try {
      customerService.getCustomerByName(firstName, lastName);
      fail("Expected a ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals(
          "Customer not found with firstName=" + firstName + " lastName=" + lastName,
          re.getMessage());
    }
    verify(customerRepository, times(1))
        .findByFirstNameAndLastName(isA(String.class), isA(String.class));
  }

  @Test
  void deleteCustomerCase1() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    customerService.deleteCustomer(customerId);
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
    verify(customerRepository, times(1)).deleteByCustomerId(isA(String.class));
  }

  @Test
  void deleteCustomerCase2() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.empty();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    try {
      customerService.deleteCustomer(customerId);
      fail("Expected a ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Customer not found with Id=" + customerId, re.getMessage());
    }
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getCustomerTotalRewardsCase1() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.empty();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    try {
      customerService.getCustomerTotalRewards(customerId);
      fail("Expected a ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Customer not found with Id=" + customerId, re.getMessage());
    }
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getCustomerTotalRewardsCase2() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);

    Optional<List<Reward>> rewardOptional = Optional.empty();
    when(rewardRepository.findByCustomerId(customerId)).thenReturn(rewardOptional);

    CustomerTotalRewardsReport customerTotalRewardsReport =
        customerService.getCustomerTotalRewards(customerId);
    assertEquals(customerId, customerTotalRewardsReport.getCustomerId());
    assertEquals(firstName, customerTotalRewardsReport.getFirstName());
    assertEquals(lastName, customerTotalRewardsReport.getLastName());
    assertEquals(BigDecimal.ZERO, customerTotalRewardsReport.getTotalRewardPoints());
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
    verify(rewardRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getCustomerTotalRewardsCase3() {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);

    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewaredDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewaredDateTime1);
    String rewardId2 = "reward-test-id";
    String customerId2 = "customer-test-id";
    String transactionId2 = "transaction-test-id";
    LocalDateTime rewaredDateTime2 = LocalDateTime.now();
    BigDecimal points2 = new BigDecimal(20);
    Reward reward2 = Reward.builder().build();
    reward2.setRewardId(rewardId2);
    reward2.setCustomerId(customerId2);
    reward2.setPoints(points2);
    reward2.setTransactionId(transactionId2);
    reward2.setDateTime(rewaredDateTime2);
    List<Reward> rewards = Arrays.asList(reward1, reward2);
    Optional<List<Reward>> rewardOptional = Optional.of(rewards);
    when(rewardRepository.findByCustomerId(customerId)).thenReturn(rewardOptional);

    CustomerTotalRewardsReport customerTotalRewardsReport =
        customerService.getCustomerTotalRewards(customerId);
    assertEquals(customerId, customerTotalRewardsReport.getCustomerId());
    assertEquals(firstName, customerTotalRewardsReport.getFirstName());
    assertEquals(lastName, customerTotalRewardsReport.getLastName());
    assertEquals(new BigDecimal(30), customerTotalRewardsReport.getTotalRewardPoints());
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
    verify(rewardRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getCustomerRewardsForMonthCase1() {
    Integer year = 2023;
    Month month = Month.JANUARY;

    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.empty();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);
    try {
      customerService.getCustomerRewardsForMonth(customerId, year, month);
      fail("Expected a ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Customer not found with Id=" + customerId, re.getMessage());
    }
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
  }

  @Test
  void getCustomerRewardsForMonthCase2() {
    Integer year = 2023;
    Month month = Month.JANUARY;
    LocalDate from = LocalDate.of(year, month, 1);
    LocalDate to = from.withDayOfMonth(from.getMonth().length(from.isLeapYear()));

    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);

    Optional<List<Reward>> rewardOptional = Optional.empty();
    when(rewardRepository.findByDateTimeBetween(from, to)).thenReturn(rewardOptional);

    CustomerRewardsPerMonth customerRewardsPerMonth =
        customerService.getCustomerRewardsForMonth(customerId, year, month);
    assertEquals(customerId, customerRewardsPerMonth.getCustomerId());
    assertEquals(firstName, customerRewardsPerMonth.getFirstName());
    assertEquals(lastName, customerRewardsPerMonth.getLastName());
    assertEquals(BigDecimal.ZERO, customerRewardsPerMonth.getTotalMonthRewaredPoints());
    assertEquals(year, customerRewardsPerMonth.getYear());
    assertEquals(month, customerRewardsPerMonth.getMonth());
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
    verify(rewardRepository, times(1))
        .findByDateTimeBetween(isA(LocalDate.class), isA(LocalDate.class));
  }

  @Test
  void getCustomerRewardsForMonthCase3() {
    Integer year = 2023;
    Month month = Month.JANUARY;
    LocalDate from = LocalDate.of(year, month, 1);
    LocalDate to = from.withDayOfMonth(from.getMonth().length(from.isLeapYear()));

    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    Optional<Customer> customerOptional = Optional.of(customer);
    when(customerRepository.findByCustomerId(customerId)).thenReturn(customerOptional);

    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewaredDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewaredDateTime1);
    String rewardId2 = "reward-test-id";
    String customerId2 = "customer-test-id";
    String transactionId2 = "transaction-test-id";
    LocalDateTime rewaredDateTime2 = LocalDateTime.now();
    BigDecimal points2 = new BigDecimal(20);
    Reward reward2 = Reward.builder().build();
    reward2.setRewardId(rewardId2);
    reward2.setCustomerId(customerId2);
    reward2.setPoints(points2);
    reward2.setTransactionId(transactionId2);
    reward2.setDateTime(rewaredDateTime2);
    List<Reward> rewards = Arrays.asList(reward1, reward2);
    Optional<List<Reward>> rewardOptional = Optional.of(rewards);
    when(rewardRepository.findByDateTimeBetween(from, to)).thenReturn(rewardOptional);

    CustomerRewardsPerMonth customerRewardsPerMonth =
        customerService.getCustomerRewardsForMonth(customerId, year, month);
    assertEquals(customerId, customerRewardsPerMonth.getCustomerId());
    assertEquals(firstName, customerRewardsPerMonth.getFirstName());
    assertEquals(lastName, customerRewardsPerMonth.getLastName());
    assertEquals(new BigDecimal(30), customerRewardsPerMonth.getTotalMonthRewaredPoints());
    assertEquals(year, customerRewardsPerMonth.getYear());
    assertEquals(month, customerRewardsPerMonth.getMonth());
    verify(customerRepository, times(1)).findByCustomerId(isA(String.class));
    verify(rewardRepository, times(1))
        .findByDateTimeBetween(isA(LocalDate.class), isA(LocalDate.class));
  }
}
