package com.example.couponsProject.rest;

import com.example.couponsProject.beans.Category;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.beans.Customer;
import com.example.couponsProject.exceptions.couponExceptions.CouponHasExpiredException;
import com.example.couponsProject.exceptions.couponExceptions.CouponOutOfStockException;
import com.example.couponsProject.exceptions.couponExceptions.CustomerPurchasedThisCouponYetException;
import com.example.couponsProject.exceptions.customerExceptions.CustomerDoesntExistException;
import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.service.components.loginManager.ClientType;
import com.example.couponsProject.service.components.loginManager.TokensManager;
import com.example.couponsProject.service.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("Customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController extends ClientController {

    @Autowired
    private CustomerFacade customerService;
    @Autowired
    private TokensManager tokensManager;


    /**
     * empty customer controller c'tor
     */
    public CustomerController() {
    }

    /**
     * login to customer user - NOT IN USE
     * @param email String
     * @param password String
     * @return ResponseEntity<>
     */
    @Override
    public ResponseEntity<?> login(String email, String password)  {
        try{
            return new ResponseEntity<Boolean> (customerService.login(email, password), HttpStatus.OK);
        }
        catch (EmailNotFoundException | WrongPasswordException e)
        {
            return new ResponseEntity<String> ("Incorrect email or password!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * purchase new coupon
     * @param accessToken String
     * @param coupon Coupon
     * @return ResponseEntity<>
     */
    @PostMapping("/purchaseCoupon/{accessToken}")
    public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coupon,  @PathVariable String accessToken)  {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, customerService.getCustomerDetails().getId(), ClientType.Customer))
                return new ResponseEntity<Coupon> (customerService.purchaseCoupon(coupon), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CustomerDoesntExistException | CouponOutOfStockException | CouponHasExpiredException | CustomerPurchasedThisCouponYetException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get all coupons of this customer
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/getCustomerCoupons/{accessToken}")
    public ResponseEntity<?> getCustomerCoupons( @PathVariable String accessToken)
    {
        if(tokensManager.isAccessTokenExist(accessToken, customerService.getCustomerDetails().getId(), ClientType.Customer))
            return new ResponseEntity<Collection<Coupon>> (customerService.getCustomerCoupons(), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get all coupons of this customer by category
     * @param accessToken String
     * @param category Category
     * @return ResponseEntity<>
     */
    @GetMapping("/getCustomerCouponsByCategory/{accessToken}")
    public ResponseEntity<?> getCustomerCoupons(@RequestParam Category category,  @PathVariable String accessToken)
    {
        if(tokensManager.isAccessTokenExist(accessToken, customerService.getCustomerDetails().getId(), ClientType.Customer))
            return new ResponseEntity<Collection<Coupon>> (customerService.getCustomerCoupons(category), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get all coupons of this customer by less then equal to max price limit
     * @param accessToken String
     * @param maxPrice double
     * @return ResponseEntity<>
     */
    @GetMapping("/getCustomerCouponsByMaxPrice/{accessToken}")
    public ResponseEntity<?> getCustomerCoupons(@RequestParam double maxPrice,  @PathVariable String accessToken)
    {
        if(tokensManager.isAccessTokenExist(accessToken, customerService.getCustomerDetails().getId(), ClientType.Customer))
            return new ResponseEntity<Collection<Coupon>> (customerService.getCustomerCoupons(maxPrice), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get customer details
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/getCustomerDetails/{accessToken}")
    public ResponseEntity<?> getCustomerDetails( @PathVariable String accessToken)
    {
        if(tokensManager.isAccessTokenExist(accessToken, customerService.getCustomerDetails().getId(), ClientType.Customer))
            return new ResponseEntity<Customer> (customerService.getCustomerDetails(), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get all coupons that exist in the system
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/getAllCouponsInTheSystem/{accessToken}")
    public ResponseEntity<?> getAllCouponsInTheSystem( @PathVariable String accessToken)
    {
        if(tokensManager.isAccessTokenExist(accessToken, customerService.getCustomerDetails().getId(), ClientType.Customer))
            return new ResponseEntity<Collection<Coupon>> (customerService.getAllCouponsInTheSystem(), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }
}
