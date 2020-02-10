package com.example.couponsProject.service.components.jobs;

import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.exceptions.couponExceptions.CouponDoesntExistException;
import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.service.facade.AdminFacade;
import com.example.couponsProject.service.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;

@Service
public class ExpiredCouponsCleanerService {
    @Autowired
    private AdminFacade adminFacade;
    @Autowired
    private CompanyFacade companyFacade;

    /**
     * delete all the expired coupons from the repository
     */
    public void clean()
    {
        Collection<Company> companies = adminFacade.getAllCompanies();
        for (Company comp:companies)
        {
            try
            {
                if(companyFacade.login(comp.getEmail(),comp.getPassword()))
                {
                    Collection<Coupon> coupons = companyFacade.getCompanyCoupons();
                    for(Coupon coup : coupons)
                    {
                        LocalDate todayDate = LocalDate.now();
                        LocalDate expiredDate = coup.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if(todayDate.isAfter(expiredDate))
                        {
                            try
                            {
                                companyFacade.deleteCoupon(coup.getId());
                            }
                            catch (CouponDoesntExistException e)
                            {
                                e.printStackTrace();
                                continue;
                            }

                        }
                    }
                }
            }
            catch (EmailNotFoundException | WrongPasswordException e)
            {
                e.printStackTrace();
                continue;
            }
        }
    }
}
