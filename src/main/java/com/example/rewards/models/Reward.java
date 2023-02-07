package com.example.rewards.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** @author Venkat E */
@Data
@Builder
@Document(collection = "rewards")
public class Reward {
  @Id
  @JsonProperty(access = Access.READ_ONLY)
  String rewardId;

  @NotNull BigDecimal points;

  @NotNull LocalDateTime dateTime;

  @NotEmpty String transactionId;

  @NotEmpty String customerId;
}
