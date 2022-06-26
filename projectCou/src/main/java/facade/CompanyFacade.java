package facade;

import Exceptions.*;
import beans.Category;
import beans.Company;
import beans.Coupon;
import dbdao.CompaniesDBDAO;
import dbdao.CouponsDBDAO;
import utils.ConsoleColors;
import utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CompanyFacade extends ClientFacade {
    private boolean loginSucceed = false;
    private int companyId;
    static CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
    static CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    public CompanyFacade() {
    }

    public int getCompanyId() {
        return this.companyId;
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
    public boolean login(String email, String password) throws CouponSystemException {
        if (companiesDBDAO.isCompanyExists(email, password)) {
            this.loginSucceed = true;
            System.out.println("Login succeed");
            this.companyId = companiesDBDAO.getCompanyId(email, password);
            return true;
        } else throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
    }

    /**
     * add record to coupon table in data-base
     * first check if this coupon already exists
     * it's not possible to add a coupon with the same title to an existing coupon of the same company
     *
     * @param coupon an instance of coupon, the record will be set by his values
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "the date of coupon has passed" OR "coupon already exists"
     */
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (!couponsDBDAO.isFieldExists(coupon.getTitle(), companyId)) {
            coupon.setCompanyId(this.getCompanyId());
            if (coupon.getEndDate().before(DateUtils.localDateToSqlDate(LocalDate.now()))) {
                throw new CouponSystemException(CouponErrMsg.EXPIRED);
            }
            couponsDBDAO.addCoupon(coupon);
            System.out.println("Coupon added successfully");
        } else throw new CouponSystemException(CouponErrMsg.DUPLICATE);
    }

    /**
     * update record in coupon table in data-base
     * the coupon id could not be updated
     * the company id could not be updated too
     *
     * @param coupon an instance of coupon, the record will be updated by his values
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "coupon not exists"
     */
    public void updateCoupon(Coupon coupon) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (couponsDBDAO.IsCouponExists(coupon.getId())) {
            couponsDBDAO.updateCoupon(coupon);
        } else throw new CouponSystemException(CouponErrMsg.NOT_EXISTS);
    }

    /**
     * delete record in coupon table in data-base
     * the customer's coupon purchase history deleted too
     *
     * @param couponID Integer that is unique to the customer (primary-key)
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "customer not exists"
     */
    public void deleteCoupon(int couponID) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (couponsDBDAO.IsCouponExists(couponID)) {
            couponsDBDAO.deleteCoupon(couponID);
            couponsDBDAO.deleteHistoryOfCoupons(couponID);
            System.out.println("coupon " + couponID + " and all its history of purchases were deleted successfully");
        } else throw new CouponSystemException(CouponErrMsg.NOT_EXISTS);
    }

    /**
     * gets all the coupons of the company
     *
     * @return collection of all coupons
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "coupon not exists"
     */
    public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return couponsDBDAO.getAllCouponsOfCompany(this.companyId);
    }

    /**
     * gets all the coupons of one category of the company
     *
     * @param category the category to filter by it the coupons returns
     * @return collection of coupons by category filter
     * @throws CouponSystemException "couldn't log in password or email incorrect"
     */
    public ArrayList<Coupon> getCouponByCategory(Category category) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return (ArrayList<Coupon>) getCompanyCoupons().stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * gets all the coupons by maximum price of the company
     *
     * @param maxPrice the maximum price to filter by it the coupons returns
     * @return collection of coupons by maximum price filter
     * @throws CouponSystemException "couldn't log in password or email incorrect"
     */
    public ArrayList<Coupon> getCouponByMaxPrice(double maxPrice) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }

        return (ArrayList<Coupon>) getCompanyCoupons().stream()
                .filter(coupon -> coupon.getPrice() < maxPrice)
                .collect(Collectors.toList());
    }

    /**
     * gets the details of the company by fields: id, name, email, password, coupons
     *
     * @return an instance of company , his values was set by the record in data-base
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "company doesn't exist"
     */
    public Company getCompanyDetails() throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return companiesDBDAO.getOneCompany(getCompanyId());
    }

    public void logOut() {
        this.loginSucceed = false;
        System.out.println(ConsoleColors.RED_BOLD + "user logged out at " + DateUtils.beautifyDateTime(LocalDateTime.now()));
    }
}