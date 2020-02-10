package com.example.couponsProject.exceptions.companyExceptions;

public class CompanyNameAlreadyExistException extends Exception {
    /**
     * Exception that company name already exist
     * @param message String
     */
    public CompanyNameAlreadyExistException(String message)
    {
        super(message);
    }
}
