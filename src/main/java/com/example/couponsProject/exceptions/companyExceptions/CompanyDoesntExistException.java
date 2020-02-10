package com.example.couponsProject.exceptions.companyExceptions;

public class CompanyDoesntExistException extends Exception {
    /**
     * Exception that company doesn't exist
     * @param message String
     */
    public CompanyDoesntExistException(String message)
    {
        super(message);
    }
}
