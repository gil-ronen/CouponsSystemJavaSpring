package com.example.couponsProject.rest;

import com.example.couponsProject.beans.Category;
import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.exceptions.companyExceptions.CompanyDoesntExistException;
import com.example.couponsProject.exceptions.couponExceptions.CompanyIdInCouponIsUnchangeableException;
import com.example.couponsProject.exceptions.couponExceptions.CouponDoesntExistException;
import com.example.couponsProject.exceptions.couponExceptions.CouponTitleAlreadyExistInThisCompanyException;
import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.service.components.loginManager.ClientType;
import com.example.couponsProject.service.components.loginManager.TokensManager;
import com.example.couponsProject.service.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("Company")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController extends ClientController {


    @Autowired
    private CompanyFacade companyService;
    @Autowired
    private TokensManager tokensManager;


    /**
     * empty company controller c'tor
     */
    public CompanyController() {
    }


    /**
     * login to company user - NOT IN USE
     * @param email String
     * @param password String
     * @return ResponseEntity<>
     */
    @Override
    public ResponseEntity<?> login(String email, String password)  {
        try{
            return new ResponseEntity<Boolean> (companyService.login(email, password), HttpStatus.OK);
        }
        catch (EmailNotFoundException | WrongPasswordException e)
        {
            return new ResponseEntity<String> ("Incorrect email or password!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * add a new coupon
     * @param coupon Coupon
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @PostMapping("/addCoupon/{accessToken}")
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @PathVariable String accessToken) {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, companyService.getCompanyDetails().getId(), ClientType.Company))
                return new ResponseEntity<Coupon> (companyService.addCoupon(coupon), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CompanyDoesntExistException | CouponTitleAlreadyExistInThisCompanyException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update existing coupon
     * @param coupon Coupon
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @PutMapping("/updateCoupon/{accessToken}")
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon,  @PathVariable String accessToken)  {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, companyService.getCompanyDetails().getId(), ClientType.Company))
                return new ResponseEntity<Coupon> (companyService.updateCoupon(coupon), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CouponDoesntExistException | CompanyIdInCouponIsUnchangeableException | CouponTitleAlreadyExistInThisCompanyException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete existing coupon
     * @param couponID int
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @DeleteMapping("/deleteCoupon/{accessToken}")
    public ResponseEntity<?> deleteCoupon(@RequestParam int couponID,  @PathVariable String accessToken) {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, companyService.getCompanyDetails().getId(), ClientType.Company))
                return new ResponseEntity<Boolean> (companyService.deleteCoupon(couponID), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CouponDoesntExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get all company coupons
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/getCompanyCoupons/{accessToken}")
    public ResponseEntity<?> getCompanyCoupons( @PathVariable String accessToken) {
        if(tokensManager.isAccessTokenExist(accessToken, companyService.getCompanyDetails().getId(), ClientType.Company))
            return new ResponseEntity<Collection<Coupon>> (companyService.getCompanyCoupons(), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get all company coupons by category
     * @param accessToken String
     * @param category Category
     * @return ResponseEntity<>
     */
    @GetMapping("/getCompanyCouponsByCategory/{accessToken}")
    public ResponseEntity<?> getCompanyCoupons(@RequestParam Category category,  @PathVariable String accessToken) {
        if(tokensManager.isAccessTokenExist(accessToken, companyService.getCompanyDetails().getId(), ClientType.Company))
            return new ResponseEntity<Collection<Coupon>> (companyService.getCompanyCoupons(category), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get all company coupons by less then equal to max price limit
     * @param accessToken String
     * @param maxPrice double
     * @return ResponseEntity<>
     */
    @GetMapping("/getCompanyCouponsByMaxPrice/{accessToken}")
    public ResponseEntity<?> getCompanyCoupons(@RequestParam double maxPrice,  @PathVariable String accessToken) {
        if(tokensManager.isAccessTokenExist(accessToken, companyService.getCompanyDetails().getId(), ClientType.Company))
            return new ResponseEntity<Collection<Coupon>> (companyService.getCompanyCoupons(maxPrice), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get company details
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/getCompanyDetails/{accessToken}")
    public ResponseEntity<?> getCompanyDetails( @PathVariable String accessToken) {
        if(tokensManager.isAccessTokenExist(accessToken, companyService.getCompanyDetails().getId(), ClientType.Company))
            return new ResponseEntity<Company> (companyService.getCompanyDetails(), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }
}
