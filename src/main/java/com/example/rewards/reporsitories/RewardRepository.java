package com.example.rewards.reporsitories;

import com.example.rewards.models.Reward;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/** @author Venkat E */
public interface RewardRepository extends MongoRepository<Reward, Long> {
  Optional<Reward> findByRewardId(String rewardId);

  Optional<Reward> findByTransactionId(String transactionId);

  Optional<List<Reward>> findByCustomerId(String customerId);

  Optional<List<Reward>> findByDateTimeBetween(LocalDate from, LocalDate to);

  void deleteByTransactionId(String transactionId);
}
