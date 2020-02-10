package com.example.couponsProject.exceptions.loginExceptions;

public class WrongPasswordException extends Exception{
    /**
     * Exception that password is incorrect - failed to login
     * @param message String
     */
    public WrongPasswordException(String message)
    {
        super(message);
    }
}
