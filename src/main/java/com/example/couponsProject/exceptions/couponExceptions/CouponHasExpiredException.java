package com.example.couponsProject.exceptions.couponExceptions;

public class CouponHasExpiredException extends Exception {
    /**
     * Exception that coupon has been expired
     * @param message String
     */
    public CouponHasExpiredException(String message)
    {
        super(message);
    }
}
