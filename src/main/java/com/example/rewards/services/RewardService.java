package com.example.rewards.services;

import com.example.rewards.models.Reward;
import java.util.List;

/**
 * Rewared Service Interface
 *
 * @author Venkat E
 */
public interface RewardService {

  /**
   * @param rewardId
   * @return
   */
  Reward getRewardById(String rewardId);

  /** @return */
  List<Reward> getAllRewards();

  /**
   * @param transactionId
   * @return
   */
  Reward getAllRewardByTransactionId(String transactionId);

  /**
   * @param customerId
   * @return
   */
  List<Reward> getAllRewardsByCustomerId(String customerId);
}
