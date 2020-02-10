package com.example.couponsProject.exceptions.loginExceptions;

public class EmailNotFoundException extends Exception{
    /**
     * Exception that email not found - failed to login
     * @param message String
     */
    public EmailNotFoundException(String message)
    {
        super(message);
    }
}
