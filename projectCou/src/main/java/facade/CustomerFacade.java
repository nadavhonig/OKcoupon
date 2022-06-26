
package facade;

import Exceptions.CouponSystemException;
import Exceptions.LoginErrMsg;
import beans.Category;
import beans.Coupon;
import beans.Customer;
import dbdao.CouponsDBDAO;
import dbdao.CustomersDBDAO;
import org.assertj.core.util.DateUtil;
import utils.ConsoleColors;
import utils.DateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class CustomerFacade extends ClientFacade {
    private boolean loginSucceed = false;
    private int customerId;
    static CustomersDBDAO customersDBDAO = new CustomersDBDAO();
    static CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    public CustomerFacade() {
    }

    public int getCustomerId() {
        return customerId;
    }

    /**
     * the user's information (email and password) is checked against the database
     * and verifies that the information is correct
     *
     * @param email    user's email
     * @param password user's password
     * @return true if login is succeeded, false if not
     * @throws CouponSystemException "couldn't log in password or email incorrect"
     */
    public boolean login(String email, String password) throws Exceptions.CouponSystemException {
        if (customersDBDAO.isCustomerExists(email, password)) {
            this.loginSucceed = true;
            System.out.println("Login succeed");
            this.customerId = customersDBDAO.getCustomerId(email, password);
            return true;
        } else throw new Exceptions.CouponSystemException(Exceptions.LoginErrMsg.FAILED_ACCESS);
    }

    /**
     * send query to data-base with the coupon that the customer want to purchase
     * can not purchase the same coupon more than once
     * cannot be purchased if its quantity is zero
     * cannot be purchased if its expiration date has already been reached
     * After purchase the quantity in stock of this coupon decreases by one
     *
     * @param coupon the desired coupon the customer purchase
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "the date of coupon has passed" OR "no coupons left" OR "coupon not exist" OR "can't purchase more of this coupon"
     */
    public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(Exceptions.LoginErrMsg.FAILED_ACCESS);
        }
        if (couponsDBDAO.IsCouponExists(coupon.getId())) {
            if (!couponsDBDAO.IsCouponExistsAtCustomer(this.customerId, coupon.getId())) {
                if (coupon.getAmount() > 0) {
                    if (coupon.getEndDate().after(DateUtil.now())) {
                        if (couponsDBDAO.addCouponPurchase(getCustomerId(), coupon.getId())) {
                            coupon.setAmount(coupon.getAmount() - 1);
                            couponsDBDAO.updateCoupon(coupon);
                            System.out.println("purchase was successfully done");
                        }
                    } else throw new Exceptions.CouponSystemException(Exceptions.CouponErrMsg.EXPIRED);
                } else throw new Exceptions.CouponSystemException(Exceptions.CouponErrMsg.NO_COUPONS_LEFT);
            } else throw new Exceptions.CouponSystemException(Exceptions.CouponErrMsg.PURCHASE_FAILED);
        } else throw new Exceptions.CouponSystemException(Exceptions.CouponErrMsg.NOT_EXISTS);
    }

    /**
     * gets all the coupons of the customer
     *
     * @return collection of all coupons
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "coupon doesn't exist"
     */
    public ArrayList<Coupon> getCustomerCoupons() throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return couponsDBDAO.getAllCouponsOfCustomer(getCustomerId());
    }

    /**
     * gets all the coupons of one category of the customer
     *
     * @param category the category to filter by it the coupons returns
     * @return collection of coupons by category filter
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "coupon doesn't exist"
     */
    public ArrayList<Coupon> getCustomerCouponsByCategory(Category category) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return (ArrayList<Coupon>) getCustomerCoupons().stream()
                .filter(coupon -> coupon.getCategory().value == category.value)
                .collect(Collectors.toList());
    }

    /**
     * gets all the coupons by maximum price of the customer
     *
     * @param maxPrice the maximum price to filter by it the coupons returns
     * @return collection of coupons by maximum price filter
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "coupon doesn't exist"
     */
    public ArrayList<Coupon> getCustomerCouponsByPrice(double maxPrice) throws Exceptions.CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return (ArrayList<Coupon>) getCustomerCoupons().stream()
                .filter(coupon -> coupon.getPrice() < maxPrice)
                .collect(Collectors.toList());
    }

    /**
     * gets the details of the company by fields: id, name, email, password, coupons
     *
     * @return an instance of company , his values was set by the record in data-base
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "customer doesn't exist"
     */
    public Customer getCustomerDetails() throws Exceptions.CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return customersDBDAO.getOneCustomer(getCustomerId());
    }

    /**
     * gets all available coupons to purchase (for testing only)
     *
     * @return a list of coupons that are available for purchase
     * @throws CouponSystemException "couldn't log in password or email incorrect"
     */
    public ArrayList<Coupon> getAvailableCoupons() throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return couponsDBDAO.getAvailableCoupons();
    }

    public void logOut() {
        this.loginSucceed = false;
        System.out.println(ConsoleColors.RED_BOLD + "user logged out at " + DateUtils.beautifyDateTime(LocalDateTime.now()));
    }
}

