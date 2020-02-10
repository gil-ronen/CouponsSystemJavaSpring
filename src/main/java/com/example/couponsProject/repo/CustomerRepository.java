package com.example.couponsProject.repo;

import com.example.couponsProject.beans.Category;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Checks whether a customer exists in the repository by email
     * @param email String
     * @return Boolean
     */
    public Boolean existsByEmail(String email);

    /**
     * Looking for a customer in the repository by email
     * @param email String
     * @return Customer
     */
    public Customer findByEmail(String email);

    /**
     * Looking for all customers id's in the repository that purchased a specific coupon by coupon id
     * @param couponId int
     * @return Collection<Integer>
     */
    @Query(value ="SELECT customer_id FROM coupons_system.customers_coupons WHERE coupons_id=?1", nativeQuery = true)
    public Collection<Integer> getAllCustomersIdByCouponId(int couponId);

}
