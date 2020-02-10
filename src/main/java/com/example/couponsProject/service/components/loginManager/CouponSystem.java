package com.example.couponsProject.service.components.loginManager;

import com.example.couponsProject.service.components.jobs.CouponCleanerDailyTask;
import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.service.components.jobs.TimeoutAccessTokensCleanerTask;
import com.example.couponsProject.service.facade.AdminFacade;
import com.example.couponsProject.service.facade.ClientFacade;
import com.example.couponsProject.service.facade.CompanyFacade;
import com.example.couponsProject.service.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class CouponSystem {

    @Autowired
    private AdminFacade adminFacade;
    @Autowired
    private CompanyFacade companyFacade;
    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private CouponCleanerDailyTask couponCleanerDailyTask;
    @Autowired
    private TimeoutAccessTokensCleanerTask timeoutAccessTokensCleanerTask;

    /**
     * the main login to the coupons system
     * @param email String
     * @param password String
     * @param clientType ClientType
     * @return ClientFacade
     */
    public ClientFacade login(String email, String password, ClientType clientType) {
        switch (clientType) {
            case Administrator:
                try{
                    adminFacade.login(email, password);
                }
                catch (WrongPasswordException e)
                {
                    return null;
                }
                return adminFacade;

            case Company:
                try{
                    companyFacade.login(email, password);
                }
                catch (EmailNotFoundException | WrongPasswordException e)
                {
                    return null;
                }
                return companyFacade;

            case Customer:
                try{
                    customerFacade.login(email, password);
                }
                catch (EmailNotFoundException | WrongPasswordException e)
                {
                    return null;
                }
                return customerFacade;

            default:
                return null;
        }
    }

    /**
     * invoke the CouponCleanerDailyTask
     * invoke the TimeoutAccessTokensCleanerTask
     */
    @PostConstruct
	public void init() {
        //coupon cleaner daily task invoked
        couponCleanerDailyTask.startJob();
        //clean time out access tokens task invoked
        timeoutAccessTokensCleanerTask.startJob();
	}

    /**
     * stop the CouponCleanerDailyTask
     * stop the TimeoutAccessTokensCleanerTask
     */
    @PreDestroy
    public void destroy() {
        //coupon cleaner daily task is over
        couponCleanerDailyTask.stopJob();
        //clean time out access tokens task is over
        timeoutAccessTokensCleanerTask.stopJob();
    }
}