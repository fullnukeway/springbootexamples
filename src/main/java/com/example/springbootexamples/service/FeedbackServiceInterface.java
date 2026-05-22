package com.example.springbootexamples.service;

import com.example.springbootexamples.model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackServiceInterface {

    // Method signatures for FeedbackService
    public Feedback save(Feedback feedback);

    public List<Feedback> findAll();

    public Optional<Feedback> findById(Integer id);

    public void deleteById(Integer id);

    public long count();

}
