package com.example.springbootexamples.controller;


import com.example.springbootexamples.model.Customer;
import com.example.springbootexamples.repository.CustomerRepository;
import com.example.springbootexamples.service.CustomerServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/customer")

public class CustomerController {

    CustomerServiceInterface customerService;

    public CustomerController(CustomerServiceInterface customerService) {
        this.customerService = customerService;
    }

    // Saving customer data
    @PostMapping("/add")
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer){
        try {
            return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED); // return the customer data, and the 201
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);                // message from the validation that we had included in the class
        }
    }

    // List all customers' data
    @GetMapping("/all")
    public ResponseEntity<Object> allCustomers(){

        try{

            List<Customer> customers = (List<Customer>) customerService.findAll();

            if(customers.size() == 0)
                throw new Exception("No customer found.");

            return new ResponseEntity<>(customers, HttpStatus.OK);

        } catch (Exception e) {

            if(e.getMessage().equals("No customer found."))
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>("An unknown error occurred.", HttpStatus.BAD_REQUEST);
        }

    }

    // Updating customer data
    @PutMapping("/update/{id}")                // Path variable on which customer are we referring to
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer){
        try{
            Customer currentCustomer = customerService.findById(id).orElseThrow(()->new Exception("No customer found."));

            currentCustomer.setFirstName(customer.getFirstName());
            currentCustomer.setLastName(customer.getLastName());
            currentCustomer.setEmail(customer.getEmail());
            currentCustomer.setPhone(customer.getPhone());

            return new ResponseEntity<>(customerService.save(currentCustomer), HttpStatus.OK);   // NO_CONTENT: 204
        }
        catch (Exception e){
            if(e.getMessage().equals("No customer found."))
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>("An unknown error occurred.", HttpStatus.BAD_REQUEST);
        }
    }

    // Deleting customer data
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable("id") Integer id){
        try{
            Customer customer = customerService.findById(id).orElseThrow(()->new Exception("No customer found."));

            customerService.deleteById(id);

            return new ResponseEntity<>(customer, HttpStatus.OK);

        }catch(Exception e){
            if(e.getMessage().equals("No customer found."))
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>("An unknown error occurred.", HttpStatus.BAD_REQUEST);
        }
    }

    // Getting customer by id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Integer id){
        try{
            Customer customer = customerService.findById(id).orElseThrow(()->new Exception("No customer found."));

            return new ResponseEntity<>(customer, HttpStatus.OK);

        }catch(Exception e){
            if(e.getMessage().equals("No customer found."))
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>("An unknown error occurred.", HttpStatus.BAD_REQUEST);
        }
    }

    // Get customer by Email by LastName
    //@RequestParam == url query string

    @GetMapping("")
    public ResponseEntity<Object> getCustomerByEmailOrLastName(
            @RequestParam(name = "email", required = false, defaultValue = "") String email,
            @RequestParam(name = "lastName", required = false, defaultValue = "") String lastName
    ){
        try {

            List<Customer> customers = null;

            if (!email.isBlank() && !lastName.isBlank())
                customers = customerService.findByEmailOrLastNameContaining(email, lastName);
            else if(!email.isBlank() && lastName.isBlank())
                customers = customerService.findByEmailContaining(email);
            else if(email.isBlank() && !lastName.isBlank())
                customers = customerService.findByLastNameContaining(lastName);

            if (customers == null)
                throw new Exception("No customer found.");

            return new ResponseEntity<>(customers, HttpStatus.OK);

        }catch (Exception e){
            if(e.getMessage().equals("No customer found."))
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}