package com.example.couponsProject.repo;

import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    /**
     * Checks whether a company exists in the repository by name
     * @param name String
     * @return Boolean
     */
    public Boolean existsByName(String name);

    /**
     * Checks whether a company exists in the repository by email
     * @param email String
     * @return Boolean
     */
    public Boolean existsByEmail(String email);

    /**
     * Looking for a company in the repository by email
     * @param email String
     * @return Company
     */
    public Company findByEmail(String email);
}
