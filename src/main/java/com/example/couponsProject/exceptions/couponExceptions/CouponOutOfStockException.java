package com.example.couponsProject.exceptions.couponExceptions;

public class CouponOutOfStockException extends Exception {
    /**
     * Exception that coupon is out of stock
     * @param message String
     */
    public CouponOutOfStockException(String message)
    {
        super(message);
    }
}
