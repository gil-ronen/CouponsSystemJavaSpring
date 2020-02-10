package com.example.couponsProject.service.facade;

import com.example.couponsProject.beans.Category;
import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.beans.Customer;
import com.example.couponsProject.exceptions.companyExceptions.CompanyDoesntExistException;
import com.example.couponsProject.exceptions.couponExceptions.CompanyIdInCouponIsUnchangeableException;
import com.example.couponsProject.exceptions.couponExceptions.CouponDoesntExistException;
import com.example.couponsProject.exceptions.couponExceptions.CouponTitleAlreadyExistInThisCompanyException;
import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.repo.CompanyRepository;
import com.example.couponsProject.repo.CouponRepository;
import com.example.couponsProject.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class CompanyFacade extends ClientFacade {

    private int companyID;

    @Autowired
    private CompanyRepository companyRepo;
    @Autowired
    private CouponRepository couponRepo;
    @Autowired
    private CustomerRepository customerRepo;

    /**
     * empty company facade c'tor
     */
    public CompanyFacade() {
    }

    /**
     * full company facade c'tor
     * @param companyID int
     */
    public CompanyFacade(int companyID) {
        this.companyID = companyID;
    }

    /**
     * login to company user
     * @param email String
     * @param password String
     * @return boolean
     * @throws EmailNotFoundException if company email not found
     * @throws WrongPasswordException if company password is incorrect
     */
    @Override
    public boolean login(String email, String password) throws EmailNotFoundException, WrongPasswordException {
        Company comp = companyRepo.findByEmail(email);
        if (comp == null) {
            //throw Exception
            throw new EmailNotFoundException("failed to login! company email not found!");
        } else {
            if (!comp.getPassword().equals(password)) {
                //throw Exception
                throw new WrongPasswordException("failed to login! password is incorrect!");
            }
            this.companyID = comp.getId();
            return true;
        }
    }

    /**
     * add a new coupon
     * @param coupon Coupon
     * @return Coupon
     * @throws CompanyDoesntExistException If you try to add a coupon to a company that doesn't exist
     * @throws CouponTitleAlreadyExistInThisCompanyException A new coupon with the same title can't be added to an existing coupon from the same company
     */
    public Coupon addCoupon(Coupon coupon) throws CompanyDoesntExistException, CouponTitleAlreadyExistInThisCompanyException {
        Company comp = companyRepo.findById(this.companyID).get();
        if (comp == null) {
            //throw Exception
            throw new CompanyDoesntExistException("company doesn't exist!");
        } else {
            coupon.setCompanyID(this.companyID); //if this company tries to add coupon to another company
            for (Coupon coup : comp.getCoupons()) {
                if (coup.getTitle().equals(coupon.getTitle())) {
                    //throw Exception
                    throw new CouponTitleAlreadyExistInThisCompanyException("coupon title is already exist!");
                }
            }
            comp.getCoupons().add(coupon);
            companyRepo.save(comp);
            return coupon;
        }
    }

    /**
     * update existing coupon
     * @param coupon Coupon
     * @return Coupon
     * @throws CouponDoesntExistException You can't update a coupon that doesn't exist
     * @throws CompanyIdInCouponIsUnchangeableException You can't update a company id in coupon
     * @throws CouponTitleAlreadyExistInThisCompanyException A coupon with the same title can't be updated to an existing coupon title from the same company
     */
    public Coupon updateCoupon(Coupon coupon) throws CouponDoesntExistException, CompanyIdInCouponIsUnchangeableException, CouponTitleAlreadyExistInThisCompanyException {
        if (!couponRepo.existsById(coupon.getId())) {
            //throw Exception
            throw new CouponDoesntExistException("coupon doesn't exist!");
        } else {
            if (coupon.getCompanyID()!=this.companyID) {
                //throw Exception
                throw new CompanyIdInCouponIsUnchangeableException("can't change company ID in exist coupon!");
            } else {
                Collection<Coupon> coupons = getCompanyCoupons();

                //checks if coupon title is already exist
                for (Coupon coup:coupons) {
                    if (!coup.equals(coupon) && coup.getTitle().equals(coupon.getTitle())) {
                        //throw Exception
                        throw new CouponTitleAlreadyExistInThisCompanyException("coupon title is already exist!");
                    }
                }
                for (Coupon coup:coupons)
                {
                    if(coup.getId()==coupon.getId())
                    {
                        return couponRepo.save(coupon);
                    }
                }
                return null;
            }
        }
    }

    /**
     * delete existing coupon
     * @param couponID int
     * @return boolean
     * @throws CouponDoesntExistException You can't delete a coupon that doesn't exist
     */
    public boolean deleteCoupon(int couponID) throws CouponDoesntExistException {
        if (!couponRepo.existsById(couponID)) {
            //throw Exception
            throw new CouponDoesntExistException("coupon doesn't exist!");
        }
        Coupon coupon = couponRepo.getOne(couponID);
        //Step 1: delete coupon from all customers that purchased it
        Collection<Integer> customersId = customerRepo.getAllCustomersIdByCouponId(couponID);
        for (Integer id:customersId)
        {
            Customer customer = customerRepo.findById(id).get();
            customer.getCoupons().remove(coupon);
        }

        //Step 2: delete coupon from the company that created it
        Company company = companyRepo.findById(coupon.getCompanyID()).get();
        company.getCoupons().remove(coupon);

        //Step 3: delete coupon from the coupons repository
        couponRepo.deleteById(couponID);
        return true;
    }


    /**
     * get all company coupons
     * @return Collection<Coupon>
     */
    public Collection<Coupon> getCompanyCoupons() {
        return couponRepo.findCouponsByCompanyID(this.companyID);
    }

    /**
     * get all company coupons by category
     * @param category Category
     * @return Collection<Coupon>
     */
    public Collection<Coupon> getCompanyCoupons(Category category) {
        return couponRepo.findByCompanyIDAndCategory(this.companyID, category);
    }

    /**
     * get all company coupons by less then equal to max price limit
     * @param maxPrice double
     * @return Collection<Coupon>
     */
    public Collection<Coupon> getCompanyCoupons(double maxPrice) {
        return couponRepo.findCouponsByCompanyIDAndPriceLessThanEqual(this.companyID, maxPrice);
    }

    /**
     * get company details
     * @return Company
     */
    public Company getCompanyDetails() {
        return companyRepo.findById(this.companyID).get();
    }
}
