package dao;

import Exceptions.CouponSystemException;
import beans.Company;

import java.util.ArrayList;

public interface CompaniesDAO {
    boolean isCompanyExists(String email, String password);

    void addCompany(Company company) throws CouponSystemException;

    void updateCompany(Company company);

    void deleteCompany(int companyId) throws CouponSystemException;

    ArrayList<Company> getAllCompanies() throws CouponSystemException;

    Company getOneCompany(int companyId) throws CouponSystemException;

}
