package com.example.rewards.reporsitories;

import com.example.rewards.models.Customer;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongo Repository to crud customer details to mongodb.
 *
 * @author Venkat E
 */
public interface CustomerRepository extends MongoRepository<Customer, Long> {

  /**
   * @param firstName
   * @param lastName
   * @return
   */
  Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);

  /**
   * @param id
   * @return
   */
  Optional<Customer> findByCustomerId(String id);

  /** @param id */
  void deleteByCustomerId(String id);
}
