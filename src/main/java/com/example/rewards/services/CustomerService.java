package com.example.rewards.services;

import com.example.rewards.models.Customer;
import com.example.rewards.models.CustomerRewardsPerMonth;
import com.example.rewards.models.CustomerTotalRewardsReport;
import java.time.Month;
import java.util.List;

/** @author Venkat E */
public interface CustomerService {
  Customer createCustomer(Customer customer);

  Customer updateCustomer(Customer customer);

  List<Customer> getAllCustomers();

  Customer getCustomerById(String id);

  Customer getCustomerByName(String firstName, String lastName);

  void deleteCustomer(String id);

  CustomerTotalRewardsReport getCustomerTotalRewards(String customerId);

  CustomerRewardsPerMonth getCustomerRewardsForMonth(String customerId, Integer year, Month month);
}
