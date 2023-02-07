package com.example.rewards.services;

import com.example.rewards.models.Customer;
import com.example.rewards.models.CustomerRewardsPerMonth;
import com.example.rewards.models.CustomerTotalRewardsReport;
import java.time.Month;
import java.util.List;

/** @author Venkat E */
public interface CustomerService {

  /**
   * @param customer
   * @return
   */
  Customer createCustomer(Customer customer);

  /**
   * @param customer
   * @return
   */
  Customer updateCustomer(Customer customer);

  /** @return */
  List<Customer> getAllCustomers();

  /**
   * @param id
   * @return
   */
  Customer getCustomerById(String id);

  /**
   * @param firstName
   * @param lastName
   * @return
   */
  Customer getCustomerByName(String firstName, String lastName);

  /** @param id */
  void deleteCustomer(String id);

  /**
   * @param customerId
   * @return
   */
  CustomerTotalRewardsReport getCustomerTotalRewards(String customerId);

  /**
   * @param customerId
   * @param year
   * @param month
   * @return
   */
  CustomerRewardsPerMonth getCustomerRewardsForMonth(String customerId, Integer year, Month month);
}
