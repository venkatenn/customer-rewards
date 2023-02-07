package com.example.rewards.reporsitories;

import com.example.rewards.models.Customer;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/** @author Venkat E */
public interface CustomerRepository extends MongoRepository<Customer, Long> {
  Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);
  Optional<Customer> findByCustomerId(String id);
  void deleteByCustomerId(String id);
}
