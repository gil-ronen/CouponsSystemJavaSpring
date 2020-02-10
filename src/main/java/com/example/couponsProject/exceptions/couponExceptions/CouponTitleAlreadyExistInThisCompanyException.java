package com.example.couponsProject.exceptions.couponExceptions;

public class CouponTitleAlreadyExistInThisCompanyException extends Exception{
    /**
     * Exception that coupon title already exist in this company
     * @param message String
     */
    public CouponTitleAlreadyExistInThisCompanyException(String message)
    {
        super(message);
    }
}
