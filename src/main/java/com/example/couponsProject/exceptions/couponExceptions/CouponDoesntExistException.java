package com.example.couponsProject.exceptions.couponExceptions;

public class CouponDoesntExistException extends Exception {
    /**
     * Exception that coupon doesn't exist
     * @param message String
     */
    public CouponDoesntExistException(String message)
    {
        super(message);
    }
}
