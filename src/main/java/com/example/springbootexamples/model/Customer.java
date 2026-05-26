package com.example.springbootexamples.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
}



// Empty Constructor - covered by Lombok @NoArgsConstructor
// All Arguments constructor - covered by @AllArgsConstructor
// Parameterized Constructor below - removed
// Getters and Setters - Covered by Lombok @Getter and Setter
