package com.example.couponsProject.repo;

import com.example.couponsProject.beans.Category;
import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    /**
     * Looking for coupons by company id
     * @param companyID int
     * @return Collection<Coupon>
     */
    public Collection<Coupon> findCouponsByCompanyID(int companyID);

    /**
     * Looking for coupons by company id and category
     * @param companyID int
     * @param category Category
     * @return Collection<Coupon>
     */
    public Collection<Coupon> findByCompanyIDAndCategory(int companyID, Category category);

    /**
     * Looking for coupons by company id and price less then equal to max price limit
     * @param companyID int
     * @param price double
     * @return Collection<Coupon>
     */
    public Collection<Coupon> findCouponsByCompanyIDAndPriceLessThanEqual(int companyID, double price);



}
