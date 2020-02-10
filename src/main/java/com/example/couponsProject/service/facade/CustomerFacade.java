package com.example.couponsProject.service.facade;

import com.example.couponsProject.beans.Category;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.beans.Customer;
import com.example.couponsProject.exceptions.couponExceptions.CouponHasExpiredException;
import com.example.couponsProject.exceptions.couponExceptions.CouponOutOfStockException;
import com.example.couponsProject.exceptions.couponExceptions.CustomerPurchasedThisCouponYetException;
import com.example.couponsProject.exceptions.customerExceptions.CustomerDoesntExistException;
import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.repo.CouponRepository;
import com.example.couponsProject.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class CustomerFacade extends ClientFacade {

    private int customerID;

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CouponRepository couponRepo;

    /**
     * empty customer facade c'tor
     */
    public CustomerFacade() {
    }

    /**
     * full customer facade c'tor
     * @param customerID int
     */
    public CustomerFacade(int customerID) {
        this.customerID = customerID;
    }

    /**
     * login to customer user
     * @param email String
     * @param password String
     * @return boolean
     * @throws EmailNotFoundException if customer email not found
     * @throws WrongPasswordException if customer password is incorrect
     */
    @Override
    public boolean login(String email, String password) throws EmailNotFoundException, WrongPasswordException {
        Customer cust = customerRepo.findByEmail(email);
        if(cust == null)
        {
            //throw Exception
            throw new EmailNotFoundException("failed to login! customer email not found!");
        }
        else
        {
            if(!cust.getPassword().equals(password))
            {
                //throw Exception
                throw new WrongPasswordException("failed to login! password is incorrect!");
            }
            this.customerID = cust.getId();
            return true;
        }
    }

    /**
     * purchase new coupon
     * @param coupon Coupon
     * @return Coupon
     * @throws CustomerDoesntExistException  if customer doesn't exist
     * @throws CouponOutOfStockException if this coupon is out of stock
     * @throws CouponHasExpiredException if this coupon has been expired
     * @throws CustomerPurchasedThisCouponYetException if customer purchased this coupon yet
     */
    public Coupon purchaseCoupon(Coupon coupon) throws CustomerDoesntExistException, CouponOutOfStockException, CouponHasExpiredException, CustomerPurchasedThisCouponYetException {
        Customer cust = customerRepo.findById(this.customerID).get();
        if (cust == null) {
            //throw Exception
            throw new CustomerDoesntExistException("customer doesn't exist!");
        } else {
            if(coupon.getAmount()<=0)
            {
                //throw Exception
                throw new CouponOutOfStockException("this coupon is out of stock");
            }
            LocalDate todayDate = LocalDate.now();
            LocalDate expiredDate = coupon.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(todayDate.isAfter(expiredDate))
            {
                //throw Exception
                throw new CouponHasExpiredException("this coupon has expired");
            }
            Collection<Coupon> coupons = cust.getCoupons();
            if(coupons.contains(coupon))
            {
                //throw Exception
                throw new CustomerPurchasedThisCouponYetException("customer purchased this coupon yet!");
            }
            coupon.setAmount(coupon.getAmount()-1);
            coupons.add(coupon);
            customerRepo.saveAndFlush(cust);
            return coupon;
        }
    }

    /**
     * get all coupons of this customer
     * @return Collection<Coupon>
     */
    public Collection<Coupon> getCustomerCoupons()
    {
        return customerRepo.findById(this.customerID).get().getCoupons();
    }

    /**
     * get all coupons of this customer by category
     * @param category Category
     * @return Collection<Coupon>
     */
    public Collection<Coupon> getCustomerCoupons(Category category)
    {
        Collection<Coupon> coupons = getCustomerCoupons();
        Collection<Coupon> filteredCategoryCoupons = new ArrayList<Coupon>();
        for (Coupon c:coupons)
        {
            if(c.getCategory()==category)
            {
                filteredCategoryCoupons.add(c);
            }
        }
        return filteredCategoryCoupons;
    }

    /**
     * get all coupons of this customer by less then equal to max price limit
     * @param maxPrice double
     * @return Collection<Coupon>
     */
    public Collection<Coupon> getCustomerCoupons(double maxPrice)
    {
        Collection<Coupon> coupons = getCustomerCoupons();
        Collection<Coupon> filteredCategoryCoupons = new ArrayList<Coupon>();
        for (Coupon c:coupons)
        {
            if(c.getPrice()<=maxPrice)
            {
                filteredCategoryCoupons.add(c);
            }
        }
        return filteredCategoryCoupons;
    }

    /**
     * get customer details
     * @return Customer
     */
    public Customer getCustomerDetails()
    {
        return customerRepo.findById(this.customerID).get();
    }

    /**
     * get all coupons that exist in the system
     * @return Collection<Coupon>
     */
    public Collection<Coupon> getAllCouponsInTheSystem()
    {
        return couponRepo.findAll();
    }



}
