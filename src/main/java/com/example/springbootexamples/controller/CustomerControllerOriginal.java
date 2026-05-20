//
//package com.example.springbootexamples.controller;
//
//
//import com.example.springbootexamples.model.Customer;
//import com.example.springbootexamples.repository.CustomerRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.ExecutionException;
//
//@RestController
//public class CustomerControllerOriginal {
//
//    CustomerRepository customerRepository;  // Constructor injection is favored over @Autowired injection
//
//    public CustomerControllerOriginal(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//
//    // Saving customer data
//    //      - ResponseEntity<Object> instead of ResponseEntity<String>
//    //      - because we want to
//    @PostMapping("/add")
//    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
//
//        // For debugging purposes
////        System.out.println("******************");
////        System.out.println(customer.getFirstName());
////        System.out.println(customer.getLastName());
////        System.out.println(customer.getEmail());
////        System.out.println("******************");
//
//        try {
//
//            return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
//        } catch (Exception e) {
//
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//
//    @GetMapping("/all")
//    public ResponseEntity<Object> allCustomer() {
//        try {
//
//            // Different ways of retrieving response from findAll.
//            List<Customer> customers = (List<Customer>) customerRepository.findAll();
//            // ArrayList<Customer> customers = (ArrayList<Customer>) customerRepository.findAll();
//            // Iterable<Customer> customers = customerRepository.findAll();
//
//            // In Java, is the Array data type fixed in length? Yes (therefore cannot use in this instance)
//            // List is an interface for an ArrayList. Is an ArrayList fixed in length? No (therefore can be used here)
//
//            if (customers.size() == 0)
//                throw new Exception("No customer found.");
//
//            return new ResponseEntity<>(customers, HttpStatus.OK);
//
//        } catch (Exception e) {
//
//            if (e.getMessage().equals("No customer found."))
//                return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
//
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
//
//        }
//
//
//    }
//
//    // Updating customer data
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Object> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer) {
//
//        try {
//
//            // Optional<Customer> currentCustomer = customerRepository.findById(id); // IntelliJ's recommended approach - response might be null
//            Customer currentCustomer = customerRepository.findById(id).orElseThrow(
//                    () -> new Exception("No customer found")
//            );  // Preferred approach
//
//            currentCustomer.setFirstName(customer.getFirstName());
//            currentCustomer.setLastName(customer.getLastName());
//            currentCustomer.setEmail(customer.getEmail());
//            currentCustomer.setPhone(customer.getPhone());
//
//            return new ResponseEntity<>(customerRepository.save(currentCustomer), HttpStatus.OK); // Common approach: NO_CONTENT (204) but returns nothing. Prefer OK (200) and returns saved content
//
//        } catch (Exception e) {
//
//            if (e.getMessage().equals("No customer found"))
//                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//
//            return new ResponseEntity<>("An unknown error occurred.", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    // deleted customer data
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteCustomerById(@PathVariable("id") Integer id) {
//
//        try {
//
//            Customer currentCustomer = customerRepository.findById(id).orElseThrow(
//                    () -> new Exception("No customer found")
//            );  // Preferred approach
//
//            customerRepository.deleteById(id);
//            return new ResponseEntity<>(String.format("Customer %d deleted.", id), HttpStatus.OK);
//
//        } catch (Exception e) {
//
//            if (e.getMessage().equals("No customer found"))
//                return new ResponseEntity<>(String.format("Customer %d does not exist.", id), HttpStatus.NOT_FOUND);
//
//            return new ResponseEntity<>("An unknown error occurred.", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // getting customer data
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Integer id) {
//
//        try {
//            Customer currentCustomer = customerRepository.findById(id).orElseThrow(
//                    () -> new Exception("No customer found")
//            );  // Preferred approach
//
//            return new ResponseEntity<>(currentCustomer, HttpStatus.OK);
//
//        } catch (Exception e) {
//
//            if (e.getMessage().equals("No customer found"))
//                return new ResponseEntity<>(String.format("Customer %d does not exist.", id), HttpStatus.NOT_FOUND);
//
//            return new ResponseEntity<>("An unknown error occurred.", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // Get customer by Email by LastName
//    // @RequestParam == url query string
//    @GetMapping("")
//    public ResponseEntity<Object> getCustomerByEmailOrLastName(
//            @RequestParam(name = "email", required = false, defaultValue = "") String email,
//            @RequestParam(name = "lastName", required = false, defaultValue = "") String lastName
//    ){
//        try {
//
//            List<Customer> customers = null;
//
//            if (!email.isBlank() && !lastName.isBlank())
//                customers = customerRepository.findByEmailOrLastNameContaining(email, lastName);
//            else if(!email.isBlank() && lastName.isBlank())
//                customers = customerRepository.findByEmailContaining(email);
//            else if(email.isBlank() && !lastName.isBlank())
//                customers = customerRepository.findByLastNameContaining(lastName);
//
//            if (customers == null)
//                throw new Exception("No customer found.");
//
//            return new ResponseEntity<>(customers, HttpStatus.OK);
//
//        } catch (Exception e){
//            if(e.getMessage().equals("No customer found."))
//                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//}