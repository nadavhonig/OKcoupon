package db_util;

import beans.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseManager {
    public static final int MAX_CONNECTIONS = 10;
    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String USER_NAME = "root";
    public static final String USER_PASS = "12345678";
    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS coupon_project";
    public static final String DROP_DB = "DROP DATABASE coupon_project";
    public static final String CREATE_ALL_CATEGORIES = "INSERT INTO coupon_project.categories (name) VALUES (?)";
    public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS coupon_project.companies (\n" +
            "`id` INT NOT NULL AUTO_INCREMENT,\n" +
            "`name` VARCHAR(45) NOT NULL,\n" +
            "`email` VARCHAR(45) NOT NULL,\n" +
            "`password` VARCHAR(45) NOT NULL,\n" +
            " PRIMARY KEY (`id`))";

    public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS coupon_project.customers (\n" +
            " `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "`first_name` VARCHAR(45) NOT NULL,\n" +
            "`last_name` VARCHAR(45) NOT NULL,\n" +
            "`email` VARCHAR(45) NOT NULL,\n" +
            "`password` VARCHAR(45) NOT NULL,\n" +
            " PRIMARY KEY (`id`))";

    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE coupon_project.categories (\n" +
            " `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "`name` VARCHAR(45) NOT NULL,\n" +
            "PRIMARY KEY (`id`))";

    public static final String CREATE_TABLE_COUPONS =
            "CREATE TABLE IF NOT EXISTS coupon_project.coupons (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `company_id` INT NOT NULL,\n" +
                    "  `category_id` INT NOT NULL,\n" +
                    "  `title` VARCHAR(45) NOT NULL,\n" +
                    "  `description` VARCHAR(45) NOT NULL,\n" +
                    "  `start_date` DATE NOT NULL,\n" +
                    "  `end_date` DATE NOT NULL,\n" +
                    "  `amount` INT NOT NULL,\n" +
                    "  `price` DOUBLE NOT NULL,\n" +
                    "  `image` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
                    "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `company_id`\n" +
                    "    FOREIGN KEY (`company_id`)\n" +
                    "    REFERENCES `coupon_project`.`companies` (`id`)\n" +
                    //when delete company the coupons with same companyId also delete
                    "     ON DELETE CASCADE  \n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `category_id`\n" +
                    "    FOREIGN KEY (`category_id`)\n" +
                    "    REFERENCES `coupon_project`.`categories` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);";

    public static final String CREATE_TABLE_CUSTOMERS_COUPONS =
            "CREATE TABLE IF NOT EXISTS coupon_project.customers_coupons (\n" +
                    "  `customer_id` INT NOT NULL,\n" +
                    "  `coupon_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
                    "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `customer_id`\n" +
                    "    FOREIGN KEY (`customer_id`)\n" +
                    "    REFERENCES coupon_project.customers (`id`)\n" +
                    //when delete customer the purchases with same customerId also delete
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `coupon_id`\n" +
                    "    FOREIGN KEY (`coupon_id`)\n" +
                    "    REFERENCES `coupon_project`.`coupons` (`id`)\n" +
                    //when delete coupon the purchases with same couponId also delete
                    "     ON DELETE CASCADE  \n" +
                    "    ON UPDATE NO ACTION);";

    /**
     * create the schema in data-base
     */
    public static void createDb() {
        try {
            DataBaseUtils.runQuery(CREATE_DB);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * delete the schema from data-base
     */
    public static void dropDb() {
        try {
            DataBaseUtils.runQuery(DROP_DB);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * create the table of companies
     * fields: id, name; email, password
     */
    public static void createTableCompanies() {
        try {
            DataBaseUtils.runQuery(CREATE_TABLE_COMPANIES);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * create the table of customers
     * fields: id, first-name, last-name, email, password
     */
    public static void createTableCustomers() {
        try {
            DataBaseUtils.runQuery(CREATE_TABLE_CUSTOMERS);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * create table of categories by the Enum names and values
     * fields: id, name
     */
    public static void createTableCategories() {

        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            DataBaseUtils.runQuery(CREATE_TABLE_CATEGORIES);

            for (Category category : Category.values()) {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ALL_CATEGORIES);
                preparedStatement.setString(1, category.toString());
                preparedStatement.execute();

            }
            ConnectionPool.getInstance().restoreConnection(connection);
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        }

    }

    /**
     * create the table of coupons
     * fields: id, company-id, category-id, title, description, start-date, end-date, amount, price, image
     */
    public static void createTableCoupons() {
        try {
            DataBaseUtils.runQuery(CREATE_TABLE_COUPONS);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * create table of history of purchase coupons by customers
     * fields: coupon-id, customer-id
     */
    public static void createTableCustomersCoupons() {
        try {
            DataBaseUtils.runQuery(CREATE_TABLE_CUSTOMERS_COUPONS);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * one method creates all data-base tables: Categories, Companies, Customers, Coupons, Customers_Coupons
     */
    public static void createAllDataBase() {
        dropDb();
        createDb();
        createTableCategories();
        createTableCompanies();
        createTableCustomers();
        createTableCoupons();
        createTableCustomersCoupons();
    }
}
