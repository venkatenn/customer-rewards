package com.example.rewards.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** @author Venkat E */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {
  @Id
  @JsonProperty(access = Access.READ_ONLY)
  String customerId;

  @NotEmpty(message = "Please provide a valid first name")
  @Size(max = 100)
  String firstName;

  @NotEmpty(message = "Please provide a valid last name")
  @Size(max = 100)
  String lastName;
  // Not adding more details as this is demo app.
}
