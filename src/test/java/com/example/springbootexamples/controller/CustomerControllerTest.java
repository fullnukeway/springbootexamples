package com.example.springbootexamples.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springbootexamples.model.Customer;
import com.example.springbootexamples.repository.CustomerRepository;
import com.example.springbootexamples.repository.FeedbackRepository;
import com.example.springbootexamples.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)   // disable security filters while testing
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer1, customer2;

    private List<Customer> customerList = new ArrayList<>();

    private static final String API_ENDPOINT = "https://localhost:8080/customer";

    @BeforeEach
    void setUp() {

        feedbackRepository.deleteAll(); // Delete feedbacks first
        customerRepository.deleteAll();  // delete all customer records before each test

        // arrange - precondition
        customer1 = Customer
                .builder()
                .firstName("Jim")
                .lastName("Doe")
                .email("jimdoe@gmail.com")
                .build();

        customer2 = Customer
                .builder()
                .firstName("Sam")
                .lastName("Smith")
                .email("samsmith@gmail.com")
                .build();

        customerList.add(customer1);
        customerList.add(customer2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("JUNIT test: add a customer via addCustomer method in the CustomerController")

    void addCustomer() throws Exception {

        // arrange - prepare
        String requestBody = objectMapper.writeValueAsString(customer1);

        // act - action or behavior (saving a customer)
        ResultActions resultActions = mockMvc.perform(post(API_ENDPOINT.concat("/add"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        // assert - verify the output
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value(customer1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer1.getLastName()))
                .andExpect(jsonPath("$.email").value(customer1.getEmail()));

    }

    @Test
    @DisplayName("JUNIT test: get all customers from CustomerController")
    void allCustomers() throws Exception {

        // Arrange - prepare
        customerRepository.saveAll(customerList);   //save customer 1 and customer 2

        // Act - action or behavior
        ResultActions resultActions = mockMvc.perform(get(API_ENDPOINT.concat("/all")));

        // Assert - verify output
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(customerList.size())));
    }

    @Test
    @DisplayName("JUNIT test: update a customer via CustomerController")
    void updateCustomer() throws Exception {

        // Arrange
        customerRepository.save(customer1);
        Customer customer = customerRepository.findById(customer1.getId()).get();

        customer.setFirstName("John_v2");
        customer.setLastName("Doe_v2");
        customer.setEmail("johndoev2@gmail.com");
        customer.setPhone("1234567890");

        String requestBody = objectMapper.writeValueAsString(customer);

        // Act
        ResultActions resultActions = mockMvc.perform(put(API_ENDPOINT.concat("/update/{id}"), customer1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));


        // Assert
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer.getPhone()));

    }

    @Test
    @DisplayName("TODO JUNIT test:")
    void deleteCustomerById() throws Exception {
    }

    @Test
    @DisplayName("TODO JUNIT test:")
    void getCustomerById() throws Exception {
    }

    @Test
    @DisplayName("TODO JUNIT test:")
    void getCustomerByEmailOrLastName() {
    }

}


