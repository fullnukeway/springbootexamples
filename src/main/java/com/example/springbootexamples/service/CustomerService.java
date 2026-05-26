package com.example.springbootexamples.service;

import com.example.springbootexamples.model.Customer;
import com.example.springbootexamples.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerServiceInterface{

    CustomerRepository customerRepository;  // Constructor injection is favored over @Autowired injection

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findByEmailOrLastNameContaining(String email, String lastName) {
        return customerRepository.findByEmailOrLastNameContaining(email, lastName);
    }

    @Override
    public List<Customer> findByEmailContaining(String email) {
        return customerRepository.findByEmailContaining(email);
    }

    @Override
    public List<Customer> findByLastNameContaining(String lastName) {
        return customerRepository.findByLastNameContaining(lastName);
    }
}