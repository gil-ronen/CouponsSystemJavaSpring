package com.example.couponsProject.exceptions.customerExceptions;

public class CustomerDoesntExistException extends Exception{
    /**
     * Exception that customer doesn't exist
     * @param message String
     */
    public CustomerDoesntExistException(String message)
    {
        super(message);
    }
}
