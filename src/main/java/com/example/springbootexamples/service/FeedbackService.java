package com.example.springbootexamples.service;

import com.example.springbootexamples.model.Customer;
import com.example.springbootexamples.model.Feedback;
import com.example.springbootexamples.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService implements FeedbackServiceInterface{

    FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Optional<Feedback> findById(Integer id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public long count() {
        return feedbackRepository.count();
    }

    //Delete Feedback based on a customer Id
    public void deleteByCustomerId(Integer id){
        feedbackRepository.deleteByCustomerId(id);
    }

    public List<Feedback> findByCustomer(Customer customer){
        return feedbackRepository.findByCustomer(customer);
    }


}
