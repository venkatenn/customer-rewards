package com.example.rewards.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.rewards.models.Customer;
import com.example.rewards.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** @author Venkat E */
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
  @MockBean CustomerService customerService;
  @Autowired private MockMvc mockMvc;

  final ObjectMapper MAPPER = new ObjectMapper();
  ObjectWriter WRITER;

  @BeforeEach
  public void before() {
    MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    WRITER = MAPPER.writer().withDefaultPrettyPrinter();
  }

  @Test
  void getAllCustomers() throws Exception {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    List<Customer> customers = Arrays.asList(customer, customer);
    when(customerService.getAllCustomers()).thenReturn(customers);

    mockMvc
        .perform(get("/customer/all"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(equalTo(2))));
    verify(customerService, times(1)).getAllCustomers();
  }

  @Test
  void getCustomerById() throws Exception {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    when(customerService.getCustomerById(customerId)).thenReturn(customer);

    mockMvc
        .perform(get("/customer/{customerId}", customerId))
        .andDo(print())
        .andExpect(status().isOk());
    verify(customerService, times(1)).getCustomerById(isA(String.class));
  }

  @Test
  void getCustomerByFirstAndLastName() throws Exception {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    when(customerService.getCustomerByName(firstName, lastName)).thenReturn(customer);

    mockMvc
        .perform(get("/customer").param("firstName", firstName).param("lastName", lastName))
        .andDo(print())
        .andExpect(status().isOk());
    verify(customerService, times(1)).getCustomerByName(isA(String.class), isA(String.class));
  }

  @Test
  void createCustomer() throws Exception {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    String customerJson = WRITER.writeValueAsString(customer);

    mockMvc
        .perform(post("/customer").contentType(MediaType.APPLICATION_JSON).content(customerJson))
        .andDo(print())
        .andExpect(status().isOk());
    verify(customerService, times(1)).createCustomer(isA(Customer.class));
  }

  @Test
  void updateCustomer() throws Exception {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    String customerJson = WRITER.writeValueAsString(customer);

    mockMvc
        .perform(
            put("/customer/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
        .andDo(print())
        .andExpect(status().isOk());
    verify(customerService, times(1)).updateCustomer(isA(Customer.class));
  }

  @Test
  void deleteCustomer() throws Exception {
    String customerId = "customerId";

    mockMvc
        .perform(
            delete("/customer/{customerId}", customerId).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    verify(customerService, times(1)).deleteCustomer(isA(String.class));
  }

  @Test
  void getCustomerTotalRewards() throws Exception {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    when(customerService.getCustomerById(customerId)).thenReturn(customer);

    mockMvc
        .perform(get("/customer/total-rewards/{customerId}", customerId))
        .andDo(print())
        .andExpect(status().isOk());
    verify(customerService, times(1)).getCustomerTotalRewards(isA(String.class));
  }

  @Test
  void getCustomerRewardsInAMonth() throws Exception {
    String customerId = "customerId";
    String firstName = "firstName";
    String lastName = "lastName";
    Customer customer = Customer.builder().build();
    customer.setCustomerId(customerId);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    when(customerService.getCustomerById(customerId)).thenReturn(customer);

    mockMvc
        .perform(
            get("/customer/rewards-per-month")
                .param("customerId", customerId)
                .param("year", "2023")
                .param("month", Month.FEBRUARY.toString()))
        .andDo(print())
        .andExpect(status().isOk());
    verify(customerService, times(1))
        .getCustomerRewardsForMonth(isA(String.class), isA(Integer.class), isA(Month.class));
  }
}
