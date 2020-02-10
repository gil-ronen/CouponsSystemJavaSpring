package com.example.couponsProject.exceptions.companyExceptions;

public class CompanyNameIsUnchangeableException extends Exception {
    /**
     * Exception that the name of the company is unchangeable
     * @param message String
     */
    public CompanyNameIsUnchangeableException(String message)
    {
        super(message);
    }
}
