package com.example.rewards.services.impl;

import com.example.rewards.exceptions.ResourceNotFoundException;
import com.example.rewards.models.Customer;
import com.example.rewards.models.CustomerRewardsPerMonth;
import com.example.rewards.models.CustomerTotalRewardsReport;
import com.example.rewards.models.Reward;
import com.example.rewards.reporsitories.CustomerRepository;
import com.example.rewards.reporsitories.RewardRepository;
import com.example.rewards.services.CustomerService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Customer service implementation.
 *
 * @author Venkat E
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

  @Autowired private CustomerRepository customerRepository;
  @Autowired private RewardRepository rewardRepository;

  /**
   * @param customer
   * @return
   */
  @Override
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  /**
   * @param customer
   * @return
   */
  @Override
  public Customer updateCustomer(Customer customer) {
    Optional<Customer> customerDb = customerRepository.findByCustomerId(customer.getCustomerId());
    if (customerDb.isPresent()) {
      Customer customerUpdate = customerDb.get();
      customerUpdate.setFirstName(customer.getFirstName());
      customerUpdate.setLastName(customer.getLastName());
      customerRepository.save(customerUpdate);
      return customerUpdate;
    } else {
      throw new ResourceNotFoundException("Customer not found with Id=" + customer.getCustomerId());
    }
  }

  /** @return */
  @Override
  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  /**
   * Returns customer if exists. Throws ResourceNotFound exception if customer does not exists.
   *
   * @param id
   * @return
   */
  @Override
  public Customer getCustomerById(String id) {
    Optional<Customer> customerDb = customerRepository.findByCustomerId(id);
    if (customerDb.isPresent()) {
      return customerDb.get();
    } else {
      throw new ResourceNotFoundException("Customer not found with Id=" + id);
    }
  }

  /**
   * Returns customer if exists. Throws ResourceNotFound exception if customer does not exists.
   *
   * @param firstName
   * @param lastName
   * @return
   */
  @Override
  public Customer getCustomerByName(String firstName, String lastName) {
    Optional<Customer> customerDb =
        customerRepository.findByFirstNameAndLastName(firstName, lastName);
    if (customerDb.isPresent()) {
      return customerDb.get();
    } else {
      throw new ResourceNotFoundException(
          "Customer not found with firstName=" + firstName + " lastName=" + lastName);
    }
  }

  /**
   * Deletes customer if exists. Throws ResourceNotFound exception if customer does not exists.
   *
   * @param id
   */
  @Override
  public void deleteCustomer(String id) {
    Optional<Customer> customerDb = customerRepository.findByCustomerId(id);
    if (customerDb.isPresent()) {
      customerRepository.deleteByCustomerId(id);
    } else {
      throw new ResourceNotFoundException("Customer not found with Id=" + id);
    }
  }

  /**
   * Returns customer total reward points if the customer exists. Throws ResourceNotFound Exception
   * if customer does not exist.
   *
   * @param customerId
   * @return
   */
  @Override
  public CustomerTotalRewardsReport getCustomerTotalRewards(String customerId) {
    Optional<Customer> customerDb = customerRepository.findByCustomerId(customerId);
    if (customerDb.isPresent()) {
      BigDecimal totalRewardPoints = BigDecimal.ZERO;
      Optional<List<Reward>> rewardsDb = rewardRepository.findByCustomerId(customerId);
      if (rewardsDb.isPresent()) {
        for (Reward reward : rewardsDb.get()) {
          totalRewardPoints = totalRewardPoints.add(reward.getPoints());
        }
      }
      CustomerTotalRewardsReport customerTotalRewardsReport = new CustomerTotalRewardsReport();
      customerTotalRewardsReport.setTotalRewardPoints(totalRewardPoints);
      customerTotalRewardsReport.setCustomerId(customerDb.get().getCustomerId());
      customerTotalRewardsReport.setFirstName(customerDb.get().getFirstName());
      customerTotalRewardsReport.setLastName(customerDb.get().getLastName());
      return customerTotalRewardsReport;
    } else {
      throw new ResourceNotFoundException("Customer not found with Id=" + customerId);
    }
  }

  /**
   * Returns customers reward points in a month if customer exists. Throws ResourceNotFound
   * exception if customer does not exist.
   *
   * @param customerId
   * @param year
   * @param month
   * @return
   */
  @Override
  public CustomerRewardsPerMonth getCustomerRewardsForMonth(
      String customerId, Integer year, Month month) {
    Optional<Customer> customerDb = customerRepository.findByCustomerId(customerId);
    if (customerDb.isPresent()) {
      LocalDate from = LocalDate.of(year, month, 1);
      LocalDate to = from.withDayOfMonth(from.getMonth().length(from.isLeapYear()));
      Optional<List<Reward>> rewardsDb = rewardRepository.findByDateTimeBetween(from, to);
      BigDecimal totalMonthlyRewards = BigDecimal.ZERO;
      if (rewardsDb.isPresent()) {
        List<Reward> monthRewards = rewardsDb.get();
        for (Reward reward : monthRewards) {
          totalMonthlyRewards = totalMonthlyRewards.add(reward.getPoints());
        }
      }
      CustomerRewardsPerMonth customerRewardsPerMonth = new CustomerRewardsPerMonth();
      customerRewardsPerMonth.setYear(year);
      customerRewardsPerMonth.setMonth(month);
      customerRewardsPerMonth.setCustomerId(customerDb.get().getCustomerId());
      customerRewardsPerMonth.setFirstName(customerDb.get().getFirstName());
      customerRewardsPerMonth.setLastName(customerDb.get().getLastName());
      customerRewardsPerMonth.setTotalMonthRewaredPoints(totalMonthlyRewards);
      return customerRewardsPerMonth;
    } else {
      throw new ResourceNotFoundException("Customer not found with Id=" + customerId);
    }
  }
}
