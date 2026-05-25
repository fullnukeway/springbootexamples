package com.example.springbootexamples.exceptions;


public class MessageNotReadableException extends RuntimeException
{
    public MessageNotReadableException()
    {
        super("Invalid Data. Please try again.");
    }
}

