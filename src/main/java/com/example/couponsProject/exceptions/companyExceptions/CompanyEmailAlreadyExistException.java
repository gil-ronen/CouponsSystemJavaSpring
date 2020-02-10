package com.example.couponsProject.exceptions.companyExceptions;

public class CompanyEmailAlreadyExistException extends Exception{
    /**
     * Exception that company email already exist
     * @param message String
     */
    public CompanyEmailAlreadyExistException(String message)
    {
        super(message);
    }
}
