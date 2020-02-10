package com.example.couponsProject.exceptions.customerExceptions;

public class CustomerEmailAlreadyExistException extends Exception{
    /**
     * Exception that customer email already exist
     * @param message String
     */
    public CustomerEmailAlreadyExistException(String message)
    {
        super(message);
    }
}
