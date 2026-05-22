package com.example.springbootexamples.controller;

import com.example.springbootexamples.model.Customer;
import com.example.springbootexamples.model.Feedback;
import com.example.springbootexamples.service.CustomerService;
import com.example.springbootexamples.service.CustomerServiceInterface;
import com.example.springbootexamples.service.FeedbackService;
import com.example.springbootexamples.service.FeedbackServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    FeedbackService feedbackService;

    CustomerServiceInterface customerService;

    public FeedbackController(FeedbackService feedbackService, CustomerService customerService) {
        this.feedbackService = feedbackService;
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> allFeedback() {
        return new ResponseEntity<>(feedbackService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add/{customer_id}")
    public ResponseEntity<Object> addFeedback(
            @PathVariable("customer_id") Integer customer_id,
            @RequestBody Feedback feedback) {
        try {

            Feedback checkFeedback = customerService.findById(customer_id).map(
                    customer -> {
                        Feedback _feedback = new Feedback(customer.getId(), feedback.getDescription(), customer);
                        return feedbackService.save(_feedback);
                    }).orElseThrow(() -> new Exception("Customer not found."));

            return new ResponseEntity<>(checkFeedback, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }

    }

    // http://localhoust:8080/feedback/{feedback_id}
    @PutMapping("/{feedback_id}")
    public ResponseEntity<Object> updateFeedback(
            @PathVariable("feedback_id") Integer feedback_id,
            @RequestBody Feedback feedback) {
        try {

            Feedback checkFeedback = feedbackService.findById(feedback_id).map(
                    _feedback -> {

                        _feedback.setDescription(feedback.getDescription());
                        return feedbackService.save(_feedback);

                    }).orElseThrow(() -> new Exception("Feedback not found."));

            return new ResponseEntity<>(checkFeedback, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // localhost:8080/feedback/{feedback_id}
    @DeleteMapping("/{feedback_id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable Integer feedback_id){

        try {
            Feedback checkFeedback = feedbackService.findById(feedback_id).map(
                    _feedback-> {
                        feedbackService.deleteById(_feedback.getId());
                        return _feedback;
            }).orElseThrow(()-> new Exception("There was an error"));

            return new ResponseEntity<>(checkFeedback, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }

    }

    // localhost:8080/feedback/count
    @GetMapping("/count")
    public ResponseEntity<Object> countFeedback() {

        try{

            long count = feedbackService.count();

            if(count == 0)
                throw new Exception("No feedback found.");

            return new ResponseEntity<>(count, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);        }

    }

    @DeleteMapping("/customer/{customer_id}")
    public ResponseEntity<Object> deleteFeedbackbyCustomer(@PathVariable("customer_id") Integer customer_id){

        try {

            Customer customer = customerService.findById(customer_id).map(
                    _customer -> {
                        feedbackService.deleteByCustomerId(_customer.getId());
                        return _customer;
                    }).orElseThrow(() -> new Exception("Customer not found."));
        }catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }

         feedbackService.deleteByCustomerId(customer_id);

         return new ResponseEntity<>("Feedback deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<Object> getFeedbackByCustomerId(@PathVariable("customer_id") Integer customer_id){
        try {

            List<Feedback> checkFeedback = customerService.findById(customer_id).map((_customer)->{
                return feedbackService.findByCustomer(_customer);
            }).orElseThrow(()-> new Exception("There was an error."));

            return new ResponseEntity<>(checkFeedback, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

