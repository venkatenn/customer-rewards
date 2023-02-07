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
@Document(collection = "transactions")
public class Transaction {
  @Id
  @JsonProperty(access = Access.READ_ONLY)
  String transactionId;

  @NotNull(message = "Please provide a valid amount")
  BigDecimal amount;

  @NotNull(message = "Please provide a valid date time")
  LocalDateTime datetime;

  @NotEmpty(message = "Please provide a valida customer id")
  String customerId;
}
