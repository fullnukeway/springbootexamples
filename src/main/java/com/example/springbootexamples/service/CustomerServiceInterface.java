package com.example.springbootexamples.service;

import com.example.springbootexamples.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceInterface {

    // Method signatures for CustomerService
    public Customer save(Customer customer);

    public List<Customer> findAll();

    public void deleteById(Integer id);

    public Optional<Customer> findById(Integer id);

    public List<Customer> findByEmailOrLastNameContaining(String email, String lastName);

    public List<Customer> findByEmailContaining(String email);

    public List<Customer> findByLastNameContaining(String lastName);

}
