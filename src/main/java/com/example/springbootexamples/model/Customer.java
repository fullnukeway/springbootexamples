package com.example.springbootexamples.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Customer {

    // Integer id (auto-increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    //
    @Column(nullable = false)
    @NotBlank(message = "First name must not be empty")
    @Size(min = 2, message = "Minimum 2 characters for first name")
    String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name must not be empty")
    @Size(min = 2, message = "Minimum 2 characters for last name")
    String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email must not be empty")
    @Email(
            regexp = "^(?!\\.)(?!.*\\.{2})[a-zA-Z0-9._%+-]+(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email is not valid."
    )
    String email;

    @Column
    String phone;

    // Empty Constructor
    public Customer() {
    }

    // Constructor
    public Customer(String firstName, String lastName, String email, String phone) {
        // id is auto generated and therefore not required.
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
