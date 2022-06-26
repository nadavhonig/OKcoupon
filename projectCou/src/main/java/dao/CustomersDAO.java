package dao;

import Exceptions.CouponSystemException;
import beans.Customer;

import java.util.ArrayList;

public interface CustomersDAO {
    boolean isCustomerExists(String email, String password);

    void addCustomer(Customer customer) throws CouponSystemException;

    void updateCustomer(Customer customer);

    void deleteCustomer(int customerId);

    ArrayList<Customer> getAllCustomers() throws CouponSystemException;

    Customer getOneCustomer(int customerId) throws CouponSystemException;
}
