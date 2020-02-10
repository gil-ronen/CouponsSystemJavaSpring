package com.example.couponsProject.exceptions.couponExceptions;

public class CompanyIdInCouponIsUnchangeableException extends Exception{
    /**
     * Exception that the company id of this coupon is unchangeable
     * @param message String
     */
    public CompanyIdInCouponIsUnchangeableException(String message)
    {
        super(message);
    }
}
