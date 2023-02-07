package com.example.rewards.reporsitories;

import com.example.rewards.models.Reward;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Mongo repository to crud Reward details to mongodb.
 *
 * @author Venkat E
 */
public interface RewardRepository extends MongoRepository<Reward, Long> {

  /**
   * @param rewardId
   * @return
   */
  Optional<Reward> findByRewardId(String rewardId);

  /**
   * @param transactionId
   * @return
   */
  Optional<Reward> findByTransactionId(String transactionId);

  /**
   * @param customerId
   * @return
   */
  Optional<List<Reward>> findByCustomerId(String customerId);

  /**
   * @param from
   * @param to
   * @return
   */
  Optional<List<Reward>> findByDateTimeBetween(LocalDate from, LocalDate to);

  /** @param transactionId */
  void deleteByTransactionId(String transactionId);
}
