package com.example.rewards.models;

import java.math.BigDecimal;
import java.time.Month;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Venkat E
 */
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerRewardsPerMonth extends  Customer {
  Integer year;
  Month month;
  BigDecimal totalMonthRewaredPoints;
}
