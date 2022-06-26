package dbdao;

import Exceptions.*;
import beans.Customer;
import dao.CustomersDAO;
import db_util.DataBaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomersDBDAO implements CustomersDAO {
    private static final String ADD_CUSTOMER = "INSERT INTO coupon_project.customers (first_name,last_name,email,password)" +
            "VALUES (?,?,?,?);";
    private static final String UPDATE_CUSTOMER = "UPDATE coupon_project.customers" +
            " SET first_name=?,last_name=?,email=?,password=? WHERE id=?";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM coupon_project.customers";
    private static final String DELETE_CUSTOMER = "DELETE FROM coupon_project.customers WHERE id=";
    private static final String GET_ONE_CUSTOMER = "SELECT * FROM coupon_project.customers WHERE id=";
    private static final String QUERY_IS_EXISTS = "SELECT * from coupon_project.customers WHERE email=? AND password=?";
    private static final String MAIL_IS_EXISTS = "SELECT * from coupon_project.customers WHERE email=?";
    private static final String ID_IS_EXISTS = "SELECT * from coupon_project.customers WHERE id=";
    private static final String GET_CUSTOMER_ID = "SELECT id from coupon_project.customers WHERE email=? AND password=?";

    /**
     * sends a query to data-base customer-table, an indication if company exists in the table by email and password
     *
     * @param email    customer's email
     * @param password customer's password
     * @return true if exists in data-base, false if not
     */
    public boolean isCustomerExists(String email, String password) {
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
     * to get the unique id from data-base and set it in the customerFacade
     *
     * @param email    customer's email for the query
     * @param password customer's password for the query
     * @return an Integer that is unique to the customer (primary-key)
     */
    public int getCustomerId(String email, String password) {
        ResultSet resultset;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        try {
            resultset = DataBaseUtils.runQueryForResult(GET_CUSTOMER_ID, map);
            resultset.next();
            return (resultset.getInt(1));
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return 0;
    }

    /**
     * a query sent to data-base check if the record exists
     * an indication to check if to continue CRUD (update, delete, get-one customer)
     *
     * @param customerId the id of the customer to check if exists
     * @return true if exists in data-base, false if not
     */
    public boolean isIDExists(int customerId) {
        ResultSet resultset;
        try {
            resultset = (ResultSet) DataBaseUtils.runQueryForResult(ID_IS_EXISTS + customerId);
            if (resultset.next()) {
                return true;
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;
    }

    /**
     * sends a query to data-base customer-table, an indication if value of email exists in the table,
     * to avoid duplicate values. this values unique, and you can't have both customers with same values
     *
     * @param email customer's email, to check if exists for another customer in data-base
     * @return true if exists in data-base for another customer, false if not
     * @throws CouponSystemException "email already exists"
     */
    public boolean isFieldExists(String email) throws CouponSystemException {
        ResultSet resultset;
        try {
            Map<Integer, Object> fields = new HashMap<>();
            fields.put(1, email);
            resultset = DataBaseUtils.runQueryForResult(MAIL_IS_EXISTS, fields);
            if (resultset.next()) {
                throw new CouponSystemException(CustomerErrMsg.DUPLICATE_MAIL);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;
    }

    /**
     * a query to data-base, add a record to customer-table by customer's details
     *
     * @param customer an instance of company, the record will be set by his values
     * @throws CouponSystemException "can't update customer id" (edge-case)
     */
    public void addCustomer(Customer customer) throws CouponSystemException {
        try {
            Map<Integer, Object> customerFields = new HashMap<>();
            customerFields.put(1, customer.getFirstName());
            customerFields.put(2, customer.getLastName());
            customerFields.put(3, customer.getEmail());
            customerFields.put(4, customer.getPassword());
            int id = DataBaseUtils.runQueryGetId(ADD_CUSTOMER, customerFields);
            customer.setId(id);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to update an exists record in customer-table by new customer's details
     *
     * @param customer an instance of customer, the record will be updated by his values
     */
    @Override
    public void updateCustomer(Customer customer) {
        try {
            Map<Integer, Object> customerFields = new HashMap<>();
            customerFields.put(1, customer.getFirstName());
            customerFields.put(2, customer.getLastName());
            customerFields.put(3, customer.getEmail());
            customerFields.put(4, customer.getPassword());
            customerFields.put(5, customer.getId());
            DataBaseUtils.runQuery(UPDATE_CUSTOMER, customerFields);
        } catch (
                SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to delete an exists record in customer-table by customer id
     *
     * @param customerId customer's id to delete this specific customer
     */
    @Override
    public void deleteCustomer(int customerId) {
        try {
            DataBaseUtils.runQuery(DELETE_CUSTOMER + customerId);
        } catch (
                SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to get all customers in the customers-table in data-base
     *
     * @return a list of all customers in data-base
     */
    @Override
    public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_ALL_CUSTOMERS);
            while (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getInt("id"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .lastName(resultSet.getString("last_name"))
                        .firstName(resultSet.getString("first_name"))
                        .build();
                customer.setCoupons(new CouponsDBDAO().getAllCouponsOfCustomer(customer.getId()));
                customers.add(customer);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return customers;
    }

    /**
     * a query to data-base, to get specific customer
     *
     * @param customerId an Integer that identifies the specific customer
     * @return an instance of customer , his values was set by the record in data-base
     * @throws CouponSystemException "customer not exists"
     */
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        try {
            ResultSet resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_ONE_CUSTOMER + customerId);
            resultSet.next();
            Customer customer = Customer.builder()
                    .id(resultSet.getInt("id"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .lastName(resultSet.getString("last_name"))
                    .firstName(resultSet.getString("first_name"))
                    .build();
            customer.setCoupons(new CouponsDBDAO().getAllCouponsOfCustomer(customer.getId()));
            return customer;
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        throw new CouponSystemException(CustomerErrMsg.NO_EXISTS);
    }
}

