package com.example.springbootexamples.repository;

import com.example.springbootexamples.model.Customer;
import com.example.springbootexamples.model.Feedback;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    // Custom Query to delete the feedback from a customer
    @Modifying      // the @Modifying annotation is essential for updates and delete queries
    @Transactional  // the @Transactional ensures the operation is executed within ONE transaction
    @Query(value = "DELETE FROM feedback WHERE customer_id = :id", nativeQuery = true)
    void deleteByCustomerId(@Param("id") Integer id);

    // Derived Query to get the Customer of a Feedback

    List<Feedback> findByCustomer(Customer customer);

}
