package com.example.couponsProject.rest;

import com.example.couponsProject.beans.AccessToken;
import com.example.couponsProject.beans.Customer;
import com.example.couponsProject.exceptions.loginExceptions.EmailNotFoundException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.service.components.loginManager.ClientType;
import com.example.couponsProject.service.components.loginManager.CouponSystem;
import com.example.couponsProject.service.components.loginManager.TokensManager;
import com.example.couponsProject.service.facade.AdminFacade;
import com.example.couponsProject.service.facade.ClientFacade;
import com.example.couponsProject.service.facade.CompanyFacade;
import com.example.couponsProject.service.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    @Autowired
    private CouponSystem couponSystem;
    @Autowired
    private TokensManager tokensManager;

    /**
     * The gateway to the coupons system from the client side. login to the user account.
     * @param email String
     * @param password String
     * @param type ClientType
     * @return ResponseEntity<>
     */
    @GetMapping("/Login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password, @RequestParam ClientType type)  {
        try{
            ClientFacade clientFacade = couponSystem.login(email, password, type);
            AccessToken accessToken;
            if(clientFacade!=null)
                switch (type) {
                    case Administrator:
                        //CREATES ACCESS TOKEN AND ADD IT TO THE LIST
                        accessToken = new AccessToken(0,ClientType.Administrator);
                        tokensManager.addNewAccessToken(accessToken);
                        return new ResponseEntity<String> (accessToken.toJason(), HttpStatus.OK);

                    case Company:
                        CompanyFacade companyFacade =(CompanyFacade)clientFacade;
                        //CREATES ACCESS TOKEN AND ADD IT TO THE LIST
                        accessToken = new AccessToken(companyFacade.getCompanyDetails().getId(),ClientType.Company);
                        tokensManager.addNewAccessToken(accessToken);
                        return new ResponseEntity<String> (accessToken.toJason(), HttpStatus.OK);

                    case Customer:
                        CustomerFacade customerFacade =(CustomerFacade)clientFacade;
                        //CREATES ACCESS TOKEN AND ADD IT TO THE LIST
                        accessToken = new AccessToken(customerFacade.getCustomerDetails().getId(),ClientType.Customer);
                        tokensManager.addNewAccessToken(accessToken);
                        return new ResponseEntity<String> (accessToken.toJason(), HttpStatus.OK);


                    default:
                        return new ResponseEntity<String> ("Incorrect email or password!", HttpStatus.BAD_REQUEST);
                }
            else
                return new ResponseEntity<String> ("Incorrect email or password!", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String> ("Incorrect email or password!", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * check if the token is still valid
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/Auth")
    public ResponseEntity<?> isAccessTokenValid(@RequestParam String accessToken)
    {
        if(tokensManager.isClientRegistered(accessToken))
            return new ResponseEntity<String> ("{\"authorized\":true}", HttpStatus.OK);
        else
            return new ResponseEntity<String> ("{\"authorized\":false}", HttpStatus.OK);
    }

    /**
     * the user logged out from his account
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/Logout")
    public ResponseEntity<?> logout(@RequestParam String accessToken)
    {
        if(tokensManager.logoutAccessToken(accessToken))
            return new ResponseEntity<String> ("{\"logout\":true}", HttpStatus.OK);
        else
            return new ResponseEntity<String> ("{\"logout\":false}", HttpStatus.OK);
    }
}
