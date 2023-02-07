package com.example.rewards.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rewards.exceptions.ResourceNotFoundException;
import com.example.rewards.models.Reward;
import com.example.rewards.reporsitories.RewardRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** @author Venkat E */
class RewardServiceImplTest {
  @Mock RewardRepository rewardRepository;
  @InjectMocks RewardServiceImpl rewardService;

  @BeforeEach
  public void before() {
    MockitoAnnotations.openMocks(this);
  }

  /** Test to get reward using Id. */
  @Test
  void getRewardById() {
    String rewardId = "reward-test-id";
    String customerId = "customer-test-id";
    String transactionId = "transaction-test-id";
    LocalDateTime rewaredDateTime = LocalDateTime.now();
    BigDecimal points = new BigDecimal(10);
    Reward reward = Reward.builder().build();
    reward.setRewardId(rewardId);
    reward.setCustomerId(customerId);
    reward.setPoints(points);
    reward.setTransactionId(transactionId);
    reward.setDateTime(rewaredDateTime);
    Optional<Reward> rewardOptional = Optional.of(reward);
    when(rewardRepository.findByRewardId(rewardId)).thenReturn(rewardOptional);

    Reward rewardResult = rewardService.getRewardById(rewardId);

    assertEquals(rewardId, rewardResult.getRewardId());
    assertEquals(customerId, rewardResult.getCustomerId());
    assertEquals(transactionId, rewardResult.getTransactionId());
    assertEquals(rewaredDateTime, rewardResult.getDateTime());
    assertEquals(points, rewardResult.getPoints());
    verify(rewardRepository, times(1)).findByRewardId(isA(String.class));
  }

  /** Test to get reward for a non existing reward id. */
  @Test
  void getRewardByIdForNonExistingReward() {
    String rewardId = "reward-test-id";
    Optional<Reward> rewardOptional = Optional.empty();
    when(rewardRepository.findByRewardId(rewardId)).thenReturn(rewardOptional);
    try {
      rewardService.getRewardById(rewardId);
      fail("Expected ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Reward not found with Id=" + rewardId, re.getMessage());
    }
    verify(rewardRepository, times(1)).findByRewardId(isA(String.class));
  }

  /** Test to all rewards. */
  @Test
  void getAllRewards() {
    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewaredDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewaredDateTime1);
    List<Reward> rewards = Arrays.asList(reward1, reward1);
    when(rewardRepository.findAll()).thenReturn(rewards);

    List<Reward> rewardsResult = rewardService.getAllRewards();
    for (Reward reward : rewardsResult) {
      assertEquals(reward1, reward);
    }
    verify(rewardRepository, times(1)).findAll();
  }

  /** Test to reward using transaction Id. */
  @Test
  void getAllRewardByTransactionId() {
    String rewardId = "reward-test-id";
    String customerId = "customer-test-id";
    String transactionId = "transaction-test-id";
    LocalDateTime rewaredDateTime = LocalDateTime.now();
    BigDecimal points = new BigDecimal(10);
    Reward reward = Reward.builder().build();
    reward.setRewardId(rewardId);
    reward.setCustomerId(customerId);
    reward.setPoints(points);
    reward.setTransactionId(transactionId);
    reward.setDateTime(rewaredDateTime);
    Optional<Reward> rewardOptional = Optional.of(reward);
    when(rewardRepository.findByTransactionId(transactionId)).thenReturn(rewardOptional);

    Reward rewardResult = rewardService.getAllRewardByTransactionId(transactionId);

    assertEquals(rewardId, rewardResult.getRewardId());
    assertEquals(customerId, rewardResult.getCustomerId());
    assertEquals(transactionId, rewardResult.getTransactionId());
    assertEquals(rewaredDateTime, rewardResult.getDateTime());
    assertEquals(points, rewardResult.getPoints());
    verify(rewardRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to ger reward for a non existing transaction. */
  @Test
  void getAllRewardByTransactionIdForNonExistingTransaction() {
    String transactionId = "transaction-test-id";
    Optional<Reward> rewardOptional = Optional.empty();
    when(rewardRepository.findByTransactionId(transactionId)).thenReturn(rewardOptional);
    try {
      rewardService.getAllRewardByTransactionId(transactionId);
      fail("Expected ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Reward not found transaction with Id=" + transactionId, re.getMessage());
    }
    verify(rewardRepository, times(1)).findByTransactionId(isA(String.class));
  }

  /** Test to get all rewards using customer Id. */
  @Test
  void getAllRewardsByCustomerId() {
    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewaredDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewaredDateTime1);
    Optional<List<Reward>> rewardsOptional = Optional.of(Arrays.asList(reward1, reward1));
    when(rewardRepository.findByCustomerId(customerId1)).thenReturn(rewardsOptional);

    List<Reward> rewardsResult = rewardService.getAllRewardsByCustomerId(customerId1);
    for (Reward reward : rewardsResult) {
      assertEquals(reward1, reward);
    }
    verify(rewardRepository, times(1)).findByCustomerId(isA(String.class));
  }

  /** Test to all rewards for non existing customer Id. */
  @Test
  void getAllRewardsByCustomerIdForNonExistingCustomerId() {
    String customerId1 = "customer-test-id";
    Optional<List<Reward>> rewardsOptional = Optional.empty();
    when(rewardRepository.findByCustomerId(customerId1)).thenReturn(rewardsOptional);

    try {
      rewardService.getAllRewardsByCustomerId(customerId1);
      fail("Expected ResourceNotFound Exception");
    } catch (ResourceNotFoundException re) {
      assertEquals("Rewards not found customer with Id=" + customerId1, re.getMessage());
    }
    verify(rewardRepository, times(1)).findByCustomerId(isA(String.class));
  }
}
