package com.example.rewards.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.rewards.models.Reward;
import com.example.rewards.services.RewardService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/** @author Venkat E */
@WebMvcTest(RewardController.class)
class RewardControllerTest {
  @MockBean RewardService rewardService;
  @Autowired private MockMvc mockMvc;

  /**
   * Test to get all rewards api.
   *
   * @throws Exception
   */
  @Test
  void getAllRewards() throws Exception {
    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewardDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewardDateTime1);
    List<Reward> rewards = Arrays.asList(reward1, reward1);
    when(rewardService.getAllRewards()).thenReturn(rewards);

    mockMvc
        .perform(get("/reward/all"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(equalTo(2))));
    verify(rewardService, times(1)).getAllRewards();
  }

  /**
   * Test to get reward by id api.
   *
   * @throws Exception
   */
  @Test
  void getRewardById() throws Exception {
    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewardDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewardDateTime1);
    when(rewardService.getRewardById(rewardId1)).thenReturn(reward1);

    mockMvc.perform(get("/reward/{rewardId}", rewardId1)).andDo(print()).andExpect(status().isOk());
    verify(rewardService, times(1)).getRewardById(isA(String.class));
  }

  /**
   * Test to get reward using transaction id api.
   *
   * @throws Exception
   */
  @Test
  void getRewardByTransactionId() throws Exception {
    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewardDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewardDateTime1);
    when(rewardService.getAllRewardByTransactionId(rewardId1)).thenReturn(reward1);

    mockMvc
        .perform(get("/reward/transaction/{transactionId}", transactionId1))
        .andDo(print())
        .andExpect(status().isOk());
    verify(rewardService, times(1)).getAllRewardByTransactionId(isA(String.class));
  }

  /**
   * Test to get rewards of a customer api.
   *
   * @throws Exception
   */
  @Test
  void getRewardsByCustomerId() throws Exception {
    String rewardId1 = "reward-test-id";
    String customerId1 = "customer-test-id";
    String transactionId1 = "transaction-test-id";
    LocalDateTime rewardDateTime1 = LocalDateTime.now();
    BigDecimal points1 = new BigDecimal(10);
    Reward reward1 = Reward.builder().build();
    reward1.setRewardId(rewardId1);
    reward1.setCustomerId(customerId1);
    reward1.setPoints(points1);
    reward1.setTransactionId(transactionId1);
    reward1.setDateTime(rewardDateTime1);
    when(rewardService.getAllRewardsByCustomerId(rewardId1))
        .thenReturn(Collections.singletonList(reward1));

    mockMvc
        .perform(get("/reward/customer/{customerId}", customerId1))
        .andDo(print())
        .andExpect(status().isOk());
    verify(rewardService, times(1)).getAllRewardsByCustomerId(isA(String.class));
  }
}
