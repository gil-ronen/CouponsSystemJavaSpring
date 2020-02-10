package com.example.couponsProject.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "COUPONS")
public class Coupon {
    private int id;
    private int companyID;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * Coupon empty c'tor
     */
    public Coupon() {
    }

    /**
     * Coupon full c'tor
     * @param companyID int
     * @param category Category
     * @param title String
     * @param description String
     * @param startDate Date
     * @param endDate Date
     * @param amount int
     * @param price double
     * @param image String
     */
    public Coupon(int companyID, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * get coupon id
     * @return int
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * set coupon id
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get company id of this coupon
     * @return int
     */
    @Column
    public int getCompanyID() {
        return companyID;
    }

    /**
     * set company id of this coupon
     * @param companyID int
     */
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    /**
     * get the category of this coupon
     * @return Category
     */
    @Column
    @Enumerated(EnumType.STRING)
    public Category getCategory() {
        return category;
    }

    /**
     * set the category of this coupon
     * @param category Category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * get coupon title
     * @return String
     */
    @Column
    public String getTitle() {
        return title;
    }

    /**
     * set coupon title
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * get coupon description
     * @return String
     */
    @Column
    public String getDescription() {
        return description;
    }

    /**
     * set coupon description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get start date of this coupon
     * @return Date
     */
    @Column
    public Date getStartDate() {
        return startDate;
    }

    /**
     * set start date of this coupon
     * @param startDate Date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * get end date of this coupon
     * @return Date
     */
    @Column
    public Date getEndDate() {
        return endDate;
    }

    /**
     * set end date of this coupon
     * @param endDate Date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * get amount of this coupon
     * @return int
     */
    @Column
    public int getAmount() {
        return amount;
    }

    /**
     * set amount of this coupon
     * @param amount int
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * get price of this coupon
     * @return double
     */
    @Column
    public double getPrice() {
        return price;
    }

    /**
     * set price of this coupon
     * @param price double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * get image of this coupon
     * @return String
     */
    @Column
    public String getImage() {
        return image;
    }

    /**
     * set image of this coupon
     * @param image String
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * represents the coupon as a string
     * @return String
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    /**
     * comparison of two coupons
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id;
    }

    /**
     * make hash from this coupon
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
