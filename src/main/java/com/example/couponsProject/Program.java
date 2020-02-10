package com.example.couponsProject;

import com.example.couponsProject.beans.Category;
import com.example.couponsProject.beans.Company;
import com.example.couponsProject.beans.Coupon;
import com.example.couponsProject.beans.Customer;
import com.example.couponsProject.service.components.loginManager.ClientType;
import com.example.couponsProject.service.components.loginManager.CouponSystem;
import com.example.couponsProject.service.facade.AdminFacade;
import com.example.couponsProject.service.facade.CompanyFacade;
import com.example.couponsProject.service.facade.CustomerFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class Program {

            public static void main(String[] args) {
                ApplicationContext ctx = SpringApplication.run(Program.class, args);

        try {
            System.out.println("GO!");

            CouponSystem couponSystem = ctx.getBean(CouponSystem.class);

            // invoke the daily job
            couponSystem.init();

            //======================================================================================================================
            // Creating an AdminFacade object and calling all business logic functions
            AdminFacade adminFacade = (AdminFacade)couponSystem.login("admin@admin.com", "admin", ClientType.Administrator);

            System.out.println("\n____________________COMPANIES____________________\n");


            Company company1 = new Company("Coca Cola", "c@c.com", "1234", null);
            Company company2 = new Company("Tnuva", "t@t.com", "1234", null);
            Company company3 = new Company("Rami Levi", "r@r.com", "4444", null);
            Company company4 = new Company("ShuferSal", "s@s.com", "1234", null);

            adminFacade.addCompany(company1);
            adminFacade.addCompany(company2);
            adminFacade.addCompany(company3);
            adminFacade.addCompany(company4);
            Company updatedCompany = adminFacade.getOneCompany(company3.getId());
            updatedCompany.setEmail("e@e.com");
            updatedCompany.setPassword("1234");
            adminFacade.updateCompany(updatedCompany);

            Company deletedCompany = adminFacade.getOneCompany(company4.getId());
            adminFacade.deleteCompany(deletedCompany.getId());

            Collection<Company> companies = adminFacade.getAllCompanies();
            for (Company c:companies)
                System.out.println(c.toString());


            System.out.println("\n____________________CUSTOMERS____________________\n");
            Customer customer1 = new Customer("Yossi", "Benayun", "e1@e.com", "1234", null);
            Customer customer2 = new Customer("Leo", "Messi", "e123@e.com", "1234", null);
            Customer customer3 = new Customer("Binyamin", "Netanyahu", "e3@e.com", "1234", null);

            adminFacade.addCustomer(customer1);
            adminFacade.addCustomer(customer2);
            adminFacade.addCustomer(customer3);

            Customer updatedCustomer = adminFacade.getOneCustomer(customer2.getId());
            updatedCustomer.setFirstName("Gil");
            updatedCustomer.setLastName("Ronen");
            updatedCustomer.setEmail("e2@e.com");
            adminFacade.updateCustomer(updatedCustomer);

            Customer deletedCustomer = adminFacade.getOneCustomer(customer3.getId());
            adminFacade.deleteCustomer(deletedCustomer.getId());

            Collection<Customer> customers = adminFacade.getAllCustomers();
            for (Customer c:customers)
                System.out.println(c.toString());

            //======================================================================================================================
            // Creating CompanyFacade objects and calling all business logic functions

            LocalDate dateStart = LocalDate.now();
            LocalDate dateEnd = dateStart.plusDays(7);
            Date date0 = Date.from(dateStart.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Date date1 = Date.from(dateEnd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            //=====COMPANY 1=====//
            CompanyFacade companyFacade = (CompanyFacade)couponSystem.login("c@c.com", "1234", ClientType.Company);

            Coupon coupon1 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Restaurant, "Hamburger meal", "includes hamburger, drink and potatoes fries", date0, date1, 20, 2.4, "https://picsum.photos/200/300");
            Coupon coupon2 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Electricity, "Samsung TV 56\"", "Smart TV Full HD", date0, date1, 760, 4.3, "https://picsum.photos/200/300");
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);

            System.out.println("\n========================================\n");
            System.out.println("\n___________Company 1 details:___________\n");
            System.out.println(companyFacade.getCompanyDetails());

            System.out.println("\n___________Company 1 coupons:___________\n");
            Collection<Coupon> company1coupons = companyFacade.getCompanyCoupons();
            for (Coupon c:company1coupons)
                System.out.println(c.toString());

            System.out.println("\n___Company 1 coupons by Electricity Category:___\n");
            for (Coupon c:companyFacade.getCompanyCoupons(Category.Electricity))
                System.out.println(c.toString());

            System.out.println("\n______Company 1 coupons by max price 4.0:______\n");
            for (Coupon c:companyFacade.getCompanyCoupons(4.0))
                System.out.println(c.toString());


            //=====COMPANY 2=====//
            companyFacade = (CompanyFacade)couponSystem.login("t@t.com", "1234", ClientType.Company);

            Coupon coupon3 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Restaurant, "Pizza", "One larg size pizza with 2 topi's ", date0, date1, 60, 7.8, "https://picsum.photos/200/300");
            Coupon coupon4 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Vacation, "Thailand trip", "Book now your next vacation to Thailand!", date0, date1, 70, 9.8, "https://picsum.photos/200/300");
            Coupon coupon5 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Food, "50% off Italian food", "Buy Italian Food for 50% discount!", date0, date1, 30, 4.1, "https://picsum.photos/200/300");
            companyFacade.addCoupon(coupon3);
            companyFacade.addCoupon(coupon4);
            companyFacade.addCoupon(coupon5);

            System.out.println("\n========================================\n");
            System.out.println("\n___________Company 2 details:___________\n");
            System.out.println(companyFacade.getCompanyDetails());

            System.out.println("\n___________Company 2 coupons:___________\n");
            Collection<Coupon> company2coupons = companyFacade.getCompanyCoupons();
            for (Coupon c:company2coupons)
                System.out.println(c.toString());

            System.out.println("\n___Company 2 coupons by Restaurant Category:___\n");
            for (Coupon c:companyFacade.getCompanyCoupons(Category.Restaurant))
                System.out.println(c.toString());

            System.out.println("\n______Company 2 coupons by max price 9.0:______\n");
            for (Coupon c:companyFacade.getCompanyCoupons(9.0))
                System.out.println(c.toString());

            //=====COMPANY 3=====//
            companyFacade = (CompanyFacade)couponSystem.login("e@e.com", "1234", ClientType.Company);

            Coupon tempCoupon = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Food, "70% off Meat food", "Buy Beef and Chicken Meat for 70% discount!", date0, date1, 30, 4.1, "https://picsum.photos/200/300");
            Coupon coupon6 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Vacation, "Greece trip", "Book now your next vacation to Greece!", date0, date1, 20, 4.3, "https://picsum.photos/200/300");
            Coupon coupon7 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Restaurant, "McDonald's Meal", "Hamburger meal", date0, date1, 60, 7.8, "https://picsum.photos/200/300");
            Coupon coupon8 = new Coupon(companyFacade.getCompanyDetails().getId(), Category.Vacation, "Cyprus trip", "Book now your next vacation to Cyprus!", date0, date1, 70, 9.8, "https://picsum.photos/200/300");

            companyFacade.addCoupon(tempCoupon);
            companyFacade.addCoupon(coupon6);
            companyFacade.addCoupon(coupon7);
            companyFacade.addCoupon(coupon8);

            Collection<Coupon> coupons = companyFacade.getCompanyCoupons();
            Coupon couponToDelete = coupons.iterator().next();
            companyFacade.deleteCoupon(couponToDelete.getId());

            coupons = companyFacade.getCompanyCoupons();
            Coupon couponToUpdate = coupons.iterator().next();
            couponToUpdate.setTitle("London trip");
            couponToUpdate.setDescription("Book now your next vacation to London!");
            couponToUpdate.setImage("https://i.picsum.photos/id/1027/200/300.jpg");
            companyFacade.updateCoupon(couponToUpdate);

            System.out.println("\n========================================\n");
            System.out.println("\n___________Company 3 details:___________\n");
            System.out.println(companyFacade.getCompanyDetails());

            System.out.println("\n___________Company 3 coupons:___________\n");
            Collection<Coupon> company3coupons = companyFacade.getCompanyCoupons();
            for (Coupon c:company3coupons)
                System.out.println(c.toString());

            System.out.println("\n___Company 3 coupons by Vacation Category:___\n");
            for (Coupon c:companyFacade.getCompanyCoupons(Category.Vacation))
                System.out.println(c.toString());

            System.out.println("\n______Company 3 coupons by max price 8.0:______\n");
            for (Coupon c:companyFacade.getCompanyCoupons(8.0))
                System.out.println(c.toString());

            //======================================================================================================================
            // Creating CustomerFacade objects and calling all business logic functions

            //=====CUSTOMER 1=====//
            CustomerFacade customerFacade = (CustomerFacade)couponSystem.login("e1@e.com", "1234", ClientType.Customer);

            for(Coupon c:company1coupons)
                customerFacade.purchaseCoupon(c);

            int i = 1;
            for(Coupon c:company2coupons)
            {
                if(i % 2 == 0)
                    customerFacade.purchaseCoupon(c);
                i++;
            }

            i = 0;
            for(Coupon c:company3coupons)
            {
                if(i % 2 == 0)
                    customerFacade.purchaseCoupon(c);
                i++;
            }

            System.out.println("\n========================================\n");
            System.out.println("\n___________Customer 1 details:___________\n");
            System.out.println(customerFacade.getCustomerDetails());

            System.out.println("\n___________Customer 1 coupons:___________\n");
            for (Coupon c:customerFacade.getCustomerCoupons())
                System.out.println(c.toString());

            System.out.println("\n___Customer 1 coupons by Vacation Restaurant:___\n");
            for (Coupon c:customerFacade.getCustomerCoupons(Category.Restaurant))
                System.out.println(c.toString());

            System.out.println("\n______Customer 1 coupons by max price 4.5:______\n");
            for (Coupon c:customerFacade.getCustomerCoupons(4.5))
                System.out.println(c.toString());

            //=====CUSTOMER 2=====//
            customerFacade = (CustomerFacade)couponSystem.login("e2@e.com", "1234", ClientType.Customer);

            for(Coupon c:company1coupons)
                customerFacade.purchaseCoupon(c);

            i = 0;
            for(Coupon c:company2coupons)
            {
                if(i % 2 == 0)
                    customerFacade.purchaseCoupon(c);
                i++;
            }

            i = 1;
            for(Coupon c:company3coupons)
            {
                if(i % 2 == 0)
                    customerFacade.purchaseCoupon(c);
                i++;
            }

            System.out.println("\n========================================\n");
            System.out.println("\n___________Customer 2 details:___________\n");
            System.out.println(customerFacade.getCustomerDetails());

            System.out.println("\n___________Customer 2 coupons:___________\n");
            for (Coupon c:customerFacade.getCustomerCoupons())
                System.out.println(c.toString());

            System.out.println("\n___Customer 2 coupons by Vacation Restaurant:___\n");
            for (Coupon c:customerFacade.getCustomerCoupons(Category.Restaurant))
                System.out.println(c.toString());

            System.out.println("\n______Customer 2 coupons by max price 4.5:______\n");
            for (Coupon c:customerFacade.getCustomerCoupons(4.5))
                System.out.println(c.toString());

            //======================================================================================================================
            // stop the daily job
            couponSystem.destroy();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
