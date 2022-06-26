
package facade;

import Exceptions.*;
import beans.Company;
import beans.Customer;
import dbdao.CompaniesDBDAO;
import dbdao.CustomersDBDAO;
import utils.ConsoleColors;
import utils.DateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade {
    private boolean loginSucceed = false;
    static CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
    static CustomersDBDAO customersDBDAO = new CustomersDBDAO();

    public AdminFacade() {
    }

    /**
     * only for Administrator there is no need to check the email and password in front of a database
     * the data should be checked as Hard-Coded.
     *
     * @param email    email will always be 'admin@admin.com'
     * @param password password will always be 'admin'
     * @return true if login is succeeded, false if not
     * @throws CouponSystemException "couldn't log in password or email incorrect"
     */
    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            this.loginSucceed = true;
            System.out.println("Login succeed");
            return true;
        }
        throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
    }

    /**
     * add record to company table in data-base
     * first check if this company already exists
     * it's not possible to add a company with the same email or name as other company
     *
     * @param company an instance of company, the record will be set by his values
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "email/name already exists" OR "company already exists"
     */
    public void addCompany(Company company) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (!companiesDBDAO.isCompanyExists(company.getEmail(), company.getPassword())) {
            if (!companiesDBDAO.isFieldExists(company.getName())) {
                if (!companiesDBDAO.isFieldExists(company.getEmail())) {
                    companiesDBDAO.addCompany(company);
                    System.out.println("company added successfully");
                } else throw new CouponSystemException(CompanyErrMsg.DUPLICATE_EMAIL);
            } else throw new CouponSystemException(CompanyErrMsg.DUPLICATE_NAME);
        } else throw new CouponSystemException(CompanyErrMsg.DUPLICATE);
    }

    /**
     * update record in company table in data-base
     * the company id could not be updated and the company name could not be updated too
     *
     * @param company an instance of company, the record will be updated by his values
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "company not exists"
     */
    public void updateCompany(Company company) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (companiesDBDAO.isIDExists(company.getId())) {
            companiesDBDAO.updateCompany(company);
            System.out.println("company updated successfully");
        } else throw new CouponSystemException(CompanyErrMsg.NOT_EXISTS);
    }

    /**
     * delete record in company table in data-base
     * all the coupons created by this company are also deleted,
     * the history of the purchase those coupons by customers deleted as well
     *
     * @param companyId Integer that is unique to the company (primary-key)
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "company not exists"
     */
    public void deleteCompany(int companyId) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (companiesDBDAO.isIDExists(companyId)) {
            companiesDBDAO.deleteCompany(companyId);
            System.out.println("company " + companyId + " and all its coupons and history of purchases were deleted successfully");
        } else throw new CouponSystemException(CompanyErrMsg.NOT_EXISTS);
    }

    /**
     * returns all existing companies in the database
     *
     * @return list of exiting companies
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "no companies in system"
     */
    public ArrayList<Company> getAllCompanies() throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        return companiesDBDAO.getAllCompanies();
    }

    /**
     * returns a specific company according to the company ID
     *
     * @param companyId Integer that is unique to the company (primary-key)
     * @return an instance of company , his values was set by the record in data-base
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "company not exists"
     */
    //todo: initialize also the coupons
    public Company getOneCompany(int companyId) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (companiesDBDAO.isIDExists(companyId)) {
            return companiesDBDAO.getOneCompany(companyId);
        } else throw new CouponSystemException(CompanyErrMsg.NOT_EXISTS);
    }

    /**
     * add record to customer table in data-base
     * first check if this customer already exists
     * it's not possible to add a customer with same email as other customer
     *
     * @param customer an instance of customer, the record will be set by his values
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "email already exists" OR "CUSTOMER already exists"
     */
    public void addCustomer(Customer customer) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (!customersDBDAO.isCustomerExists(customer.getEmail(), customer.getPassword())) {
            if (!customersDBDAO.isFieldExists(customer.getEmail())) {
                customersDBDAO.addCustomer(customer);
                System.out.println("customer added successfully");
            } else throw new CouponSystemException(CustomerErrMsg.DUPLICATE_MAIL);
        } else throw new CouponSystemException(CustomerErrMsg.DUPLICATE);
    }

    /**
     * update record in customer table in data-base
     * the customer id could not be updated
     *
     * @param customer an instance of customer, the record will be updated by his values
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "customer not exists"
     */
    public void updateCustomer(Customer customer) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (customersDBDAO.isIDExists(customer.getId())) {
            customersDBDAO.updateCustomer(customer);
            System.out.println("customer updated successfully");
        } else throw new CouponSystemException(CustomerErrMsg.DUPLICATE);
    }

    /**
     * delete record in customer table in data-base
     * delete the customer's coupon purchase history too
     *
     * @param customerId Integer that is unique to the customer (primary-key)
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "customer not exists"
     */
    public void deleteCustomer(int customerId) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (customersDBDAO.isIDExists(customerId)) {
            customersDBDAO.deleteCustomer(customerId);
            System.out.println("customer " + customerId + " and all his history of purchases were deleted successfully");
        } else throw new CouponSystemException(CustomerErrMsg.NO_EXISTS);
    }

    /**
     * returns a specific customer according to the customer ID
     *
     * @param customerId Integer that is unique to the customer (primary-key)
     * @return an instance of customer , his values was set by the record in data-base
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "customer not exists"
     */
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }
        if (customersDBDAO.isIDExists(customerId)) {
            return customersDBDAO.getOneCustomer(customerId);
        } else throw new CouponSystemException(CustomerErrMsg.NO_EXISTS);
    }

    /**
     * returns all existing customers in the database
     *
     * @return list of exiting customers
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "customer not exists" OR "no customers in system"
     */
    public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
        if (!loginSucceed) {
            throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
        }

        return customersDBDAO.getAllCustomers();
    }

    public void logOut() {
        this.loginSucceed = false;
        System.out.println(ConsoleColors.RED_BOLD + "user logged out at " + DateUtils.beautifyDateTime(LocalDateTime.now()));
    }
}
