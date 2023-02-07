package com.example.rewards.services.impl;

import com.example.rewards.exceptions.ResourceNotFoundException;
import com.example.rewards.models.Reward;
import com.example.rewards.reporsitories.RewardRepository;
import com.example.rewards.services.RewardService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reward service implementation.
 *
 * @author Venkat E
 */
@Service
@Transactional
public class RewardServiceImpl implements RewardService {

  @Autowired RewardRepository rewardRepository;

  /**
   * Returns Reward if it exists. Throws ResourceNotFoundException if it does not exist.
   *
   * @param rewardId
   * @return
   */
  @Override
  public Reward getRewardById(String rewardId) {
    Optional<Reward> rewardDb = rewardRepository.findByRewardId(rewardId);
    if (rewardDb.isPresent()) {
      return rewardDb.get();
    } else {
      throw new ResourceNotFoundException("Reward not found with Id=" + rewardId);
    }
  }

  /** @return */
  @Override
  public List<Reward> getAllRewards() {
    return rewardRepository.findAll();
  }

  /**
   * @param transactionId
   * @return
   */
  @Override
  public Reward getAllRewardByTransactionId(String transactionId) {
    Optional<Reward> rewardDb = rewardRepository.findByTransactionId(transactionId);
    if (rewardDb.isPresent()) {
      return rewardDb.get();
    } else {
      throw new ResourceNotFoundException("Reward not found transaction with Id=" + transactionId);
    }
  }

  /**
   * @param customerId
   * @return
   */
  @Override
  public List<Reward> getAllRewardsByCustomerId(String customerId) {
    Optional<List<Reward>> rewardsDb = rewardRepository.findByCustomerId(customerId);
    if (rewardsDb.isPresent()) {
      return rewardsDb.get();
    } else {
      throw new ResourceNotFoundException("Rewards not found customer with Id=" + customerId);
    }
  }
}
