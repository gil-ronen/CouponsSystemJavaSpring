package com.example.couponsProject.beans;


import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;


@Entity
@Table(name = "COMPANIES")
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private Collection<Coupon> coupons;

    /**
     * Company empty c'tor
     */
    public Company() {
    }

    /**
     * Company full c'tor
     * @param name String
     * @param email String
     * @param password String
     * @param coupons Collection<Coupon>
     */
    public Company(String name, String email, String password, Collection<Coupon> coupons) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     * get company id
     * @return int
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * set company id
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get company name
     * @return String
     */
    @Column
    public String getName() {
        return name;
    }

    /**
     * set company name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get company email
     * @return String
     */
    @Column
    public String getEmail() {
        return email;
    }

    /**
     * set company email
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get company password
     * @return String
     */
    @Column
    public String getPassword() {
        return password;
    }

    /**
     * set company password
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get company coupons collection
     * @return Collection<Coupon>
     */
    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    public Collection<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * set company coupons collection
     * @param coupons Collection<Coupon>
     */
    public void setCoupons(Collection<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * represents the company as a string
     * @return String
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

    /**
     * comparison of two companies
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id;
    }

    /**
     * make hash from this company
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
