package com.example.couponsProject.rest;

import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Customer;
import com.example.couponsProject.exceptions.companyExceptions.CompanyDoesntExistException;
import com.example.couponsProject.exceptions.companyExceptions.CompanyEmailAlreadyExistException;
import com.example.couponsProject.exceptions.companyExceptions.CompanyNameAlreadyExistException;
import com.example.couponsProject.exceptions.companyExceptions.CompanyNameIsUnchangeableException;
import com.example.couponsProject.exceptions.customerExceptions.CustomerDoesntExistException;
import com.example.couponsProject.exceptions.customerExceptions.CustomerEmailAlreadyExistException;
import com.example.couponsProject.exceptions.loginExceptions.WrongPasswordException;
import com.example.couponsProject.service.components.loginManager.ClientType;
import com.example.couponsProject.service.components.loginManager.TokensManager;
import com.example.couponsProject.service.facade.AdminFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RestController
@RequestMapping("Admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController extends ClientController {

    @Autowired
    private AdminFacade adminService;
    @Autowired
    private TokensManager tokensManager;

    /**
     * empty admin controller c'tor
     */
    public AdminController() {
    }

    /**
     * login to admin user - NOT IN USE
     * @param email String
     * @param password String
     * @return ResponseEntity<>
     */
    @Override
    public ResponseEntity<?> login(String email, String password)  {
        try{
            return new ResponseEntity<Boolean> (adminService.login(email, password), HttpStatus.OK);
        }
        catch (WrongPasswordException e)
        {
            return new ResponseEntity<String> ("Incorrect email or password!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * add a new company
     * @param accessToken String
     * @param company Company
     * @return ResponseEntity<>
     */
    @PostMapping("/addCompany/{accessToken}")
    public ResponseEntity<?> addCompany(@RequestBody Company company, @PathVariable String accessToken)  {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Company> (adminService.addCompany(company), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CompanyNameAlreadyExistException | CompanyEmailAlreadyExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * update existing company
     * @param accessToken String
     * @param company Company
     * @return ResponseEntity<>
     */
    @PutMapping("/updateCompany/{accessToken}")
    public ResponseEntity<?> updateCompany(@RequestBody Company company, @PathVariable String accessToken)  {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Company> (adminService.updateCompany(company), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CompanyDoesntExistException | CompanyEmailAlreadyExistException | CompanyNameIsUnchangeableException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete existing company
     * @param accessToken String
     * @param companyID int
     * @return ResponseEntity<>
     */
    @DeleteMapping("/deleteCompany/{accessToken}")
    public ResponseEntity<?> deleteCompany(@RequestParam int companyID, @PathVariable String accessToken) {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Boolean> (adminService.deleteCompany(companyID), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);

        }
        catch (CompanyDoesntExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * get all companies
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/getAllCompanies/{accessToken}")
    public ResponseEntity<?> getAllCompanies(@PathVariable String accessToken) {
        if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
            return new ResponseEntity<Collection<Company>> (adminService.getAllCompanies(), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get one company by company id
     * @param accessToken String
     * @param companyID int
     * @return ResponseEntity<>
     */
    @GetMapping("/getOneCompany/{accessToken}")
    public ResponseEntity<?> getOneCompany(@RequestParam int companyID, @PathVariable String accessToken)  {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Company> (adminService.getOneCompany(companyID), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CompanyDoesntExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * add new customer
     * @param accessToken String
     * @param customer Customer
     * @return ResponseEntity<>
     */
    @PostMapping("/addCustomer/{accessToken}")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @PathVariable String accessToken)  {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Customer> (adminService.addCustomer(customer), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CustomerEmailAlreadyExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * update existing customer
     * @param accessToken String
     * @param customer Customer
     * @return ResponseEntity<>
     */
    @PutMapping("/updateCustomer/{accessToken}")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable String accessToken) {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Customer> (adminService.updateCustomer(customer), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CustomerDoesntExistException | CustomerEmailAlreadyExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete existing customer
     * @param accessToken String
     * @param customerID int
     * @return ResponseEntity<>
     */
    @DeleteMapping("/deleteCustomer/{accessToken}")
    public ResponseEntity<?> deleteCustomer(@RequestParam int customerID, @PathVariable String accessToken) {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Boolean> (adminService.deleteCustomer(customerID), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CustomerDoesntExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get all customers
     * @param accessToken String
     * @return ResponseEntity<>
     */
    @GetMapping("/getAllCustomers/{accessToken}")
    public ResponseEntity<?> getAllCustomers(@PathVariable String accessToken) {
        if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
            return new ResponseEntity<Collection<Customer>>(adminService.getAllCustomers(), HttpStatus.OK);
        else
            return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
    }

    /**
     * get one customer by customer id
     * @param accessToken String
     * @param customerID int
     * @return ResponseEntity<>
     */
    @GetMapping("/getOneCustomer/{accessToken}")
    public ResponseEntity<?> getOneCustomer(@RequestParam int customerID, @PathVariable String accessToken)  {
        try{
            if(tokensManager.isAccessTokenExist(accessToken, 0, ClientType.Administrator))
                return new ResponseEntity<Customer> (adminService.getOneCustomer(customerID), HttpStatus.OK);
            else
                return new ResponseEntity<String> ("need to login!", HttpStatus.BAD_REQUEST);
        }
        catch (CustomerDoesntExistException e)
        {
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
