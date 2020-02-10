package com.example.couponsProject.service.facade;

import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.beans.Customer;

import com.example.couponsProject.exceptions.companyExceptions.CompanyDoesntExistException;
import com.example.couponsProject.exceptions.companyExceptions.CompanyEmailAlreadyExistException;
import com.example.couponsProject.exceptions.companyExceptions.CompanyNameAlreadyExistException;
import com.example.couponsProject.exceptions.companyExceptions.CompanyNameIsUnchangeableException;
import com.example.couponsProject.exceptions.customerExceptions.CustomerDoesntExistException;
import com.example.couponsProject.exceptions.customerExceptions.CustomerEmailAlreadyExistException;
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
public class AdminFacade extends ClientFacade {

    @Autowired
    private CompanyRepository companyRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CouponRepository couponRepo;

    /**
     * empty admin facade c'tor
     */
    public AdminFacade() {
    }

    /**
     * login to admin user
     * @param email String
     * @param password String
     * @return boolean
     * @throws WrongPasswordException if email or password is incorrect
     */
    @Override
    public boolean login(String email, String password) throws WrongPasswordException {
        final String adminEmail = "admin@admin.com";
        final String adminPassword = "admin";
        if (email.equals(adminEmail) && password.equals(adminPassword))
            return true;
        else
        {
            throw new WrongPasswordException("failed to login! email or password is incorrect!");
        }
    }

    /**
     * add a new company
     * @param company Company
     * @return Company
     * @throws CompanyNameAlreadyExistException You can't add a new company with the same name as an existing company
     * @throws CompanyEmailAlreadyExistException You can't add a new company with the same email as an existing company
     */
    public Company addCompany(Company company) throws CompanyNameAlreadyExistException, CompanyEmailAlreadyExistException {
        if (companyRepo.existsByName(company.getName())) {
            //throw Exception
            throw new CompanyNameAlreadyExistException("company name already exist!");
        }
        if (companyRepo.existsByEmail(company.getEmail())) {
            //throw Exception
            throw new CompanyEmailAlreadyExistException("company email already exist!");
        }
        return companyRepo.save(company);
    }

    /**
     * update existing company
     * @param company Company
     * @return Company
     * @throws CompanyDoesntExistException You can't update a company that doesn't exist
     * @throws CompanyEmailAlreadyExistException You can't update a company email with the same email as an other company
     * @throws CompanyNameIsUnchangeableException You can't rename an existing company
     */
    public Company updateCompany(Company company) throws CompanyDoesntExistException, CompanyEmailAlreadyExistException, CompanyNameIsUnchangeableException {
        if (!companyRepo.existsById(company.getId())) {
            //throw Exception
            throw new CompanyDoesntExistException("company doesn't exist!");
        } else {
            Company comp = companyRepo.getOne(company.getId());
            if (!comp.getName().equals(company.getName())) {
                //throw Exception
                throw new CompanyNameIsUnchangeableException("can't change company name!");
            } else {
                comp = companyRepo.findByEmail(company.getEmail());
                if (comp == null) {
                    return companyRepo.save(company);
                } else if (comp.getId() == company.getId()) {
                    return companyRepo.save(company);
                } else {
                    //throw Exception
                    throw new CompanyEmailAlreadyExistException("company email already exist!");
                }
            }
        }
    }

    /**
     * delete existing company
     * @param companyID int
     * @return boolean
     * @throws CompanyDoesntExistException You can't delete a company that doesn't exist
     */
    public boolean deleteCompany(int companyID) throws CompanyDoesntExistException {
        if (!companyRepo.existsById(companyID)) {
            //throw Exception
            throw new CompanyDoesntExistException("company doesn't exist!");
        }
        Collection<Coupon> allCompanyCoupons = companyRepo.getOne(companyID).getCoupons();
        for (Coupon coupon : allCompanyCoupons) {
            Collection<Customer> customers = customerRepo.findAll();
            for (Customer cust:customers)
            {
                if(cust.getCoupons().contains(coupon))
                {
                    cust.getCoupons().remove(coupon);
                }
            }
        }
        companyRepo.deleteById(companyID);
        return true;
    }


    /**
     * get all companies
     * @return Collection<Company>
     */
    public Collection<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /**
     * get one company by company id
     * @param companyID int
     * @return Company
     * @throws CompanyDoesntExistException You can't get a company that doesn't exist
     */
    public Company getOneCompany(int companyID) throws CompanyDoesntExistException {
        if (!companyRepo.existsById(companyID)) {
            //throw Exception
            throw new CompanyDoesntExistException("company doesn't exist!");
        }
        return companyRepo.getOne(companyID); //findById
    }

    /**
     * add new customer
     * @param customer Customer
     * @return Customer
     * @throws CustomerEmailAlreadyExistException You can't add a new customer with the same email as an existing customer
     */
    public Customer addCustomer(Customer customer) throws CustomerEmailAlreadyExistException {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            //throw Exception
            throw new CustomerEmailAlreadyExistException("customer email already exist!");
        }
        return customerRepo.save(customer);
    }

    /**
     * update existing customer
     * @param customer Customer
     * @return Customer
     * @throws CustomerEmailAlreadyExistException You can't update a customer email with the same email as an other customer
     * @throws CustomerDoesntExistException You can't update a customer that doesn't exist
     */
    public Customer updateCustomer(Customer customer) throws CustomerEmailAlreadyExistException, CustomerDoesntExistException {
        if (!customerRepo.existsById(customer.getId())) {
            //throw Exception
            throw new CustomerDoesntExistException("customer doesn't exist!");
        } else {
            Customer cust = customerRepo.findByEmail(customer.getEmail());
            if (cust == null) {
                return customerRepo.save(customer);
            } else if (cust.getId() == customer.getId()) {
                return customerRepo.save(customer);
            } else {
                //throw Exception
                throw new CustomerEmailAlreadyExistException("customer email already exist!");
            }
        }

    }

    /**
     * delete existing customer
     * @param customerID int
     * @return boolean
     * @throws CustomerDoesntExistException You can't delete a customer that doesn't exist
     */
    public boolean deleteCustomer(int customerID) throws CustomerDoesntExistException {
        if (!customerRepo.existsById(customerID)) {
            //throw Exception
            throw new CustomerDoesntExistException("customer doesn't exist!");
        }
        Customer customer = customerRepo.findById(customerID).get();
        customer.getCoupons().clear();
        customerRepo.deleteById(customerID);
        return true;
    }

    /**
     * get all customers
     * @return Collection<Customer>
     */
    public Collection<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * get one customer by customer id
     * @param customerID int
     * @return Customer
     * @throws CustomerDoesntExistException You can't get a customer that doesn't exist
     */
    public Customer getOneCustomer(int customerID) throws CustomerDoesntExistException {
        if (!customerRepo.existsById(customerID)) {
            //throw Exception
            throw new CustomerDoesntExistException("customer doesn't exist!");
        }
        return customerRepo.getOne(customerID);
    }
}
