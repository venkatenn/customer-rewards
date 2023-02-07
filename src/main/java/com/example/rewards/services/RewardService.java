package com.example.rewards.services;

import com.example.rewards.models.Reward;
import java.util.List;

/** @author Venkat E */
public interface RewardService {
  Reward getRewardById(String rewardId);

  List<Reward> getAllRewards();

  Reward getAllRewardByTransactionId(String transactionId);

  List<Reward> getAllRewardsByCustomerId(String customerId);
}
