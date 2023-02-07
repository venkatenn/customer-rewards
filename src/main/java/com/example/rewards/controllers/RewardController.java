package com.example.rewards.controllers;

import com.example.rewards.models.Reward;
import com.example.rewards.services.RewardService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller which handles Reward resource apis.
 *
 * @author Venkat E
 */
@RestController
@RequestMapping("reward")
public class RewardController {
  @Autowired private RewardService rewardService;

  /** @return */
  @GetMapping("all")
  public ResponseEntity<List<Reward>> getAllRewards() {
    return ResponseEntity.ok().body(rewardService.getAllRewards());
  }

  /**
   * @param rewardId
   * @return
   */
  @GetMapping("{rewardId}")
  public ResponseEntity<Reward> getRewardById(@PathVariable String rewardId) {
    return ResponseEntity.ok().body(rewardService.getRewardById(rewardId));
  }

  /**
   * @param transactionId
   * @return
   */
  @GetMapping("transaction/{transactionId}")
  public ResponseEntity<Reward> getRewardByTransactionId(@PathVariable String transactionId) {
    return ResponseEntity.ok().body(rewardService.getAllRewardByTransactionId(transactionId));
  }

  /**
   * @param customerId
   * @return
   */
  @GetMapping("customer/{customerId}")
  public ResponseEntity<List<Reward>> getRewardsByCustomerId(@PathVariable String customerId) {
    return ResponseEntity.ok().body(rewardService.getAllRewardsByCustomerId(customerId));
  }
}
