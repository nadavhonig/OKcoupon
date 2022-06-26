package dbdao;

import Exceptions.CompanyErrMsg;
import Exceptions.CouponSystemException;
import beans.Company;
import dao.CompaniesDAO;
import db_util.DataBaseUtils;

import java.sql.*;
import java.util.*;

public class CompaniesDBDAO implements CompaniesDAO {
    private static final String ADD_COMPANY = "INSERT INTO coupon_project.companies (name,email,password) VALUES (?,?,?)";
    private static final String UPDATE_COMPANY = "UPDATE coupon_project.companies SET name=?,email=?,password=? WHERE id=?";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM coupon_project.companies";
    private static final String DELETE_COMPANY = "DELETE FROM coupon_project.companies WHERE id=";
    private static final String GET_ONE_COMPANY = "SELECT * FROM coupon_project.companies WHERE id=";
    private static final String QUERY_IS_EXISTS = "SELECT * from coupon_project.companies WHERE email=? AND password=?";
    private static final String GET_ID = "SELECT id from coupon_project.companies WHERE email=? AND password=?";
    private static final String NAME_IS_EXISTS = "SELECT * from coupon_project.companies WHERE name=?";
    private static final String MAIL_IS_EXISTS = "SELECT * from coupon_project.companies WHERE email=?";

    /**
     * sends a query to data-base company-table, an indication if company exists in the table by email and password
     *
     * @param email    company's email
     * @param password company's password
     * @return true if exists in data-base, false if not
     */
    public boolean isCompanyExists(String email, String password) {
        ResultSet resultset;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        try {
            resultset = DataBaseUtils.runQueryForResult(QUERY_IS_EXISTS, map);
            if (resultset.next()) {
                return true;
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;
    }

    /**
     * sends a query to data-base company-table, an indication if value of email or name exists in the table,
     * to avoid duplicate values. this values unique, and you can't have both companies with same values
     *
     * @param nameOrEmail company's name or email, to check if exists for another company in data-base
     * @return true if exists in data-base for another company, false if not
     */
    public boolean isFieldExists(String nameOrEmail) {
        ResultSet resultset;
        Map<Integer, Object> fields = new HashMap<>();
        fields.put(1, nameOrEmail);
        try {
            if (nameOrEmail.contains("@")) {     //if its contains @ so it's must be an email!
                resultset = DataBaseUtils.runQueryForResult(MAIL_IS_EXISTS, fields);
                if (resultset.next()) {
                    return true;
                }
            }
            resultset = DataBaseUtils.runQueryForResult(NAME_IS_EXISTS, fields);
            if (resultset.next()) {
                return true;
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;

    }

    /**
     * a query sent to data-base check if the record exists
     * an indication to check if to continue CRUD (update, delete, get-one company)
     *
     * @param id the id of the company to check if exists
     * @return true if exists in data-base, false if not
     */
    public boolean isIDExists(int id) {
        ResultSet resultset;
        try {
            resultset = (ResultSet) DataBaseUtils.runQueryForResult(GET_ONE_COMPANY + id);
            if (resultset.next()) {
                return true;
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;
    }

    /**
     * to get the unique id from data-base and set it in the companyFacade
     *
     * @param email    company's email for the query
     * @param password company's password for the query
     * @return an Integer that is unique to the company (primary-key)
     * @throws CouponSystemException "company not exist"
     */
    public int getCompanyId(String email, String password) throws CouponSystemException {
        ResultSet resultset;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        try {
            resultset = DataBaseUtils.runQueryForResult(GET_ID, map);
            resultset.next();
            return (resultset.getInt(1));
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        throw new CouponSystemException(CompanyErrMsg.NOT_EXISTS);
    }

    /**
     * a query to data-base, add a record to company-table by company's details
     *
     * @param company an instance of company, the record will be set by his values
     * @throws CouponSystemException "can't update company id" (edge-case)
     */
    public void addCompany(Company company) throws CouponSystemException {
        try {
            Map<Integer, Object> companyFields = new HashMap<>();
            companyFields.put(1, company.getName());
            companyFields.put(2, company.getEmail());
            companyFields.put(3, company.getPassword());
            int id = DataBaseUtils.runQueryGetId(ADD_COMPANY, companyFields);
            company.setId(id);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to update an exists record in company-table by new company's details
     *
     * @param company an instance of company, the record will be updated by his values
     */
    @Override
    public void updateCompany(Company company) {
        try {
            Map<Integer, Object> companyFields = new HashMap<>();
            companyFields.put(1, company.getName());
            companyFields.put(2, company.getEmail());
            companyFields.put(3, company.getPassword());
            companyFields.put(4, company.getId());
            DataBaseUtils.runQuery(UPDATE_COMPANY, companyFields);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to delete an exists record in company-table by company id
     *
     * @param companyId company's id to delete this specific company
     * @throws CouponSystemException "company not exist"
     */
    @Override
    public void deleteCompany(int companyId) throws CouponSystemException {
        try {
            DataBaseUtils.runQuery(DELETE_COMPANY + companyId);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
            throw new CouponSystemException(CompanyErrMsg.NOT_EXISTS);
        }
    }

    /**
     * a query to data-base, to get all companies in the companies-table in data-base
     *
     * @return a list of all companies in data-base
     */
    @Override
    public ArrayList<Company> getAllCompanies() throws CouponSystemException {
        ArrayList<Company> companies = new ArrayList<>();
        try {
            ResultSet resultSetCompanies = (ResultSet) DataBaseUtils.runQueryForResult(GET_ALL_COMPANIES);
            while (resultSetCompanies.next()) {
                Company company = Company.builder()
                        .id(resultSetCompanies.getInt("id"))
                        .email(resultSetCompanies.getString("email"))
                        .password(resultSetCompanies.getString("password"))
                        .name(resultSetCompanies.getString("name"))
                        .build();
                company.setCoupons(new CouponsDBDAO().getAllCouponsOfCompany(company.getId()));
                companies.add(company);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return companies;
    }

    /**
     * a query to data-base, to get specific company
     *
     * @param companyID an Integer that identifies the specific company
     * @return an instance of company , his values was set by the record in data-base
     * @throws CouponSystemException "company not exist"
     */
    @Override
    public Company getOneCompany(int companyID) throws CouponSystemException {
        try {
            ResultSet resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_ONE_COMPANY + companyID);
            resultSet.next();
            Company company = Company.builder()
                    .id(resultSet.getInt("id"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .name(resultSet.getString("name"))
                    .build();
            company.setCoupons(new CouponsDBDAO().getAllCouponsOfCompany(company.getId()));
            return company;
        } catch (SQLException error) {
            System.out.println(error.getMessage());
            throw new CouponSystemException(CompanyErrMsg.NOT_EXISTS);
        }
    }
}
