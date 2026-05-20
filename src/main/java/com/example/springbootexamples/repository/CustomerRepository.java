package com.example.springbootexamples.repository;

import com.example.springbootexamples.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
    1. JpaRepository is an extension of CrudRepository with more functionalities
            - e.g. Pagination
            - For the purpose of the excercise, we only need to use CrudRepository
    2. when we write: public inter... CrudRepository <Customer, Integer>
            - <Customer, Integer> refers to <table_name, id type>
            - Ctrl/Cmd click on CrudRepository to find out more
 */
@Repository
public interface CustomerRepository extends CrudRepository <Customer, Integer> {

    // Using english terms to create queries to the database
    List<Customer> findByEmailOrLastNameContaining(String email, String lastName);

    List<Customer> findByEmailContaining(String email);

    List<Customer> findByLastNameContaining(String email);

    List<Customer> id(Integer id);

    // Default capabilities given (by CrudRepo), eg:
    // save()       -- to save and update
    // findbyId()   -- find a record by id
    // findAll()
    // count()
    // delete(Customer Object)
    // delete(Integer)

}
