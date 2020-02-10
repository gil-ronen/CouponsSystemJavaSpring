package com.example.couponsProject.beans;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Collection<Coupon> coupons;

    /**
     * Customer empty c'tor
     */
    public Customer() {
    }

    /**
     * Customer full c'tor
     * @param firstName String
     * @param lastName String
     * @param email String
     * @param password String
     * @param coupons Collection<Coupon>
     */
    public Customer(String firstName, String lastName, String email, String password, Collection<Coupon> coupons) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     * get customer id
     * @return int
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * set customer id
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get customer first name
     * @return String
     */
    @Column
    public String getFirstName() {
        return firstName;
    }

    /**
     * set customer first name
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * get customer last name
     * @return String
     */
    @Column
    public String getLastName() {
        return lastName;
    }

    /**
     * set customer last name
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get customer email
     * @return String
     */
    @Column
    public String getEmail() {
        return email;
    }

    /**
     * set customer email
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get customer password
     * @return String
     */
    @Column
    public String getPassword() {
        return password;
    }

    /**
     * set customer password
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get customer coupons collection
     * @return Collection<Coupon>
     */
    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "customers_coupons")
    public Collection<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * set customer coupons collection
     * @param coupons Collection<Coupon>
     */
    public void setCoupons(Collection<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * represents the customer as a string
     * @return String
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

    /**
     * comparison of two customers
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    /**
     * make hash from this customer
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
