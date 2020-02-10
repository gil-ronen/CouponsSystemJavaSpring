package com.example.couponsProject.exceptions.couponExceptions;

public class CustomerPurchasedThisCouponYetException extends Exception {
    /**
     * Exception that customer purchased this coupon yet
     * @param message String
     */
    public CustomerPurchasedThisCouponYetException(String message)
    {
        super(message);
    }
}
