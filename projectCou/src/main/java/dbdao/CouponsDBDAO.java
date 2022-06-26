package dbdao;

import Exceptions.CouponErrMsg;
import Exceptions.CouponSystemException;
import beans.Category;
import beans.Coupon;
import dao.CouponsDAO;
import db_util.DataBaseUtils;
import utils.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CouponsDBDAO implements CouponsDAO {
    private static final String ADD_COUPON = "INSERT INTO coupon_project.coupons" +
            "(company_id,category_id,title,description, start_date, end_date, amount,price,image) VALUES (?,?,?,?,?,?,?,?,?);";
    private final String UPDATE_COUPON = "UPDATE coupon_project.coupons" +
            " SET title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? WHERE id=?";
    private static final String DELETE_COUPON = "DELETE FROM coupon_project.coupons WHERE id=";
    private static final String GET_ALL_COUPONS = "SELECT * FROM coupon_project.coupons";
    private static final String GET_ONE_COUPON = "SELECT * FROM coupon_project.coupons WHERE id=";
    private static final String GET_ONE_COUPON_FROM_COUPON_CUSTOMER = "SELECT * FROM coupon_project.customers_coupons WHERE customer_id=? AND coupon_id=?";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO coupon_project.customers_coupons (customer_id,coupon_id) VALUES (?,?)";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM coupon_project.customers_coupons WHERE customer_id=? AND coupon_id=?";
    private static final String DELETE_HISTORY = "DELETE FROM coupon_project.customers_coupons WHERE coupon_id= ";
    private static final String IS_TITLE_EXISTS = "SELECT * from coupon_project.coupons WHERE company_id=? AND title=?";
    private static final String GET_COUPONS_BY_CUSTOMER = "SELECT coupon_id FROM coupon_project.customers_coupons WHERE customer_id=";
    private static final String GET_COUPONS_BY_COMPANY = "SELECT * FROM coupon_project.coupons WHERE company_id=";
    private static final String GET_ALL_AVAILABLE_COUPONS = "SELECT * FROM coupon_project.coupons WHERE amount>0 AND end_date>";

    /**
     * sends a query to data-base coupons-table, an indication if coupon exists in the table by title and id
     *
     * @param title coupon's title
     * @param id    coupon's id
     * @return true if exists in data-base, false if not
     */
    public boolean isFieldExists(String title, int id) {
        ResultSet resultset;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        map.put(2, title);
        try {
            resultset = DataBaseUtils.runQueryForResult(IS_TITLE_EXISTS, map);
            if (resultset.next()) {
                return true;
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;
    }

    /**
     * sends a query to data-base coupons-table, an indication if coupon exists in the table by id only
     *
     * @param couponId coupon's id
     * @return true if exists in data-base, false if not
     * @throws CouponSystemException "coupon not exists"
     */
    public boolean IsCouponExists(int couponId) throws CouponSystemException {
        ResultSet resultset;
        try {
            resultset = (ResultSet) DataBaseUtils.runQueryForResult(GET_ONE_COUPON + couponId);
            if (resultset.next()) {
                return true;
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        throw new CouponSystemException(CouponErrMsg.NOT_EXISTS);
    }

    /**
     * sends a query to data-base -customers coupons-table, an indication if this customer already  purchase this same coupon
     *
     * @param couponId   coupon's id
     * @param customerId customer's id
     * @return true if exists in data-base and the customer purchased it already , false if not
     */
    public boolean IsCouponExistsAtCustomer(int couponId, int customerId) {
        ResultSet resultset;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        try {
            resultset = DataBaseUtils.runQueryForResult(GET_ONE_COUPON_FROM_COUPON_CUSTOMER, map);
            if (resultset.next()) {
                return true;
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;
    }


    /**
     * a query to data-base, add a record to coupons-table by coupon's details
     *
     * @param coupon an instance of Coupon, the record will be set by his values
     * @throws CouponSystemException "can't update coupon id" (edge-case)
     */
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        try {
            Map<Integer, Object> couponFields = new HashMap<>();
            couponFields.put(1, coupon.getCompanyId());
            couponFields.put(2, coupon.getCategory().value);
            couponFields.put(3, coupon.getTitle());
            couponFields.put(4, coupon.getDescription());
            couponFields.put(5, coupon.getStartDate());
            couponFields.put(6, coupon.getEndDate());
            couponFields.put(7, coupon.getAmount());
            couponFields.put(8, coupon.getPrice());
            couponFields.put(9, coupon.getImage());
            int id = DataBaseUtils.runQueryGetId(ADD_COUPON, couponFields);
            coupon.setId(id);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to update an exists record in coupons-table by new coupon's details
     *
     * @param coupon an instance of Coupon, the record will be updated by his values
     */
    @Override
    public void updateCoupon(Coupon coupon) {
        try {
            Map<Integer, Object> couponFields = new HashMap<>();
            couponFields.put(1, coupon.getTitle());
            couponFields.put(2, coupon.getDescription());
            couponFields.put(3, coupon.getStartDate());
            couponFields.put(4, coupon.getEndDate());
            couponFields.put(5, coupon.getAmount());
            couponFields.put(6, coupon.getPrice());
            couponFields.put(7, coupon.getImage());
            couponFields.put(8, coupon.getId());
            DataBaseUtils.runQuery(UPDATE_COUPON, couponFields);
        } catch (
                SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to delete an exists record in coupons-table by coupon id
     *
     * @param couponId coupon's id to delete this specific coupon
     * @throws CouponSystemException "coupon not exists"
     */
    public void deleteCoupon(int couponId) throws CouponSystemException {
        try {
            DataBaseUtils.runQuery(DELETE_COUPON + couponId);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
            throw new CouponSystemException(CouponErrMsg.NOT_EXISTS);
        }
    }

    /**
     * a query to data-base, to get all available coupons for purchase coupons-table for testing only
     *
     * @return a list of coupons that are available for purchase
     */
    public ArrayList<Coupon> getAvailableCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_ALL_AVAILABLE_COUPONS +
                    "'" + DateUtils.localDateToSqlDate(LocalDate.now()) + "'");
            while (resultSet.next()) {
                Coupon coupon = sqlToJavaObj(resultSet);
                coupons.add(coupon);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return coupons;
    }

    /**
     * a query to data-base, to get all coupons that were purchased by specific customer
     *
     * @param customerId customer's id to get his specific coupons
     * @return a list of coupons that were purchased by the customer
     * @throws CouponSystemException "coupon not exists"
     */
    public ArrayList<Coupon> getAllCouponsOfCustomer(int customerId) throws CouponSystemException {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            ResultSet resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_COUPONS_BY_CUSTOMER + customerId);
            while (resultSet.next()) {
                coupons.add(getOneCouponCustomer(resultSet.getInt("coupon_id")));
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return coupons;
    }

    /**
     * a query to data-base, to get all coupons of specific customer
     *
     * @param companyId company's id to get the specific coupons
     * @return a list of coupons of the company
     * @throws CouponSystemException "coupon not exists"
     */
    public ArrayList<Coupon> getAllCouponsOfCompany(int companyId) throws CouponSystemException {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            ResultSet resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_COUPONS_BY_COMPANY + companyId);
            while (resultSet.next()) {
                coupons.add(getOneCoupon(resultSet.getInt("id")));
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }

        return coupons;
    }

    /**
     * a query to data-base, to get all coupons in the coupons-table in data-base
     *
     * @return a list of all coupons in data-base
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            ResultSet resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_ALL_COUPONS);
            while (resultSet.next()) {
                Coupon coupon = sqlToJavaObj(resultSet);
                coupons.add(coupon);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return coupons;
    }

    /**
     * to initialize object from mySQL to instance in JAVA, cast int to value string of enum
     *
     * @param categoryId an Integer from the data-base , value of specific category
     * @return the name of category in String
     */
    private Category setEnumValue(int categoryId) {
        for (Category category : Category.values()) {
            if (category.value == categoryId) {
                return category;
            }
        }
        return null;
    }

    /**
     * a query to data-base, to get specific coupon
     *
     * @param couponId an Integer that identifies the specific coupon
     * @return an instance of coupon , his values was set by the record in data-base
     * @throws CouponSystemException "coupon not exists"
     */
    @Override
    public Coupon getOneCoupon(int couponId) throws CouponSystemException {
        ResultSet resultSet;
        try {
            resultSet = (ResultSet) DataBaseUtils.runQueryForResult(GET_ONE_COUPON + couponId);
            resultSet.next();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
            throw new CouponSystemException(CouponErrMsg.NOT_EXISTS);
        }

        return sqlToJavaObj(resultSet);
    }

    /**
     * a query to data-base, to get specific coupon that customer has been purchased,
     * set the amount of coupon to one (per customer)
     *
     * @param couponId an Integer that identifies the specific coupon
     * @return an instance of coupon , his values was set by the record in data-base despite the amount
     * @throws CouponSystemException "coupon not exists"
     */
    public Coupon getOneCouponCustomer(int couponId) throws CouponSystemException {
        Coupon coupon = getOneCoupon(couponId);
        coupon.setAmount(1);
        return coupon;
    }

    /**
     * get a collection from data-base and initialize an object in JAVA by the record fields in data-base
     * in order to avoid duplicated code (get all coupons, get one coupon)
     *
     * @param resultSet a collection returns from data-base with the data of specific records in data-base table
     * @return an instance of coupon that was initialized by field's value in data-base
     */
    private Coupon sqlToJavaObj(ResultSet resultSet) {
        try {
            return Coupon.builder()
                    .id(resultSet.getInt("id"))
                    .companyId(resultSet.getInt("company_id"))
                    .amount(resultSet.getInt("amount"))
                    .category(setEnumValue(resultSet.getInt("category_id")))
                    .description(resultSet.getString("description"))
                    .endDate(resultSet.getDate("end_date"))
                    .image(resultSet.getString("image"))
                    .price(resultSet.getDouble("price"))
                    .startDate(resultSet.getDate("start_date"))
                    .title(resultSet.getString("title"))
                    .build();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    /**
     * a query to data-base, to add a records in table customers_coupons, documents the history of purchase
     *
     * @param customerId an Integer that indicates the customer's id
     * @param couponId   an Integer that indicates the coupon's id
     * @return true if the record was inserted successfully, false if not
     */
    @Override
    public boolean addCouponPurchase(int customerId, int couponId) {
        try {
            DataBaseUtils.runQuery(ADD_COUPON_PURCHASE, setParamsToMap(customerId, couponId));
            return true;
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return false;
    }

    /**
     * a query to data-base, to delete a records in table customers_coupons, it deletes the history of purchase
     *
     * @param customerId an Integer that indicates the customer's id
     * @param couponId   an Integer that indicates the coupon's id
     */
    @Override
    public void deleteCouponPurchase(int customerId, int couponId) {
        try {
            DataBaseUtils.runQuery(DELETE_COUPON_PURCHASE, setParamsToMap(customerId, couponId));
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * a query to data-base, to delete a records in table customers_coupons
     * it deletes the history of purchase by coupon id only
     *
     * @param couponId an Integer that indicates the coupon's id
     */
    public void deleteHistoryOfCoupons(int couponId) {
        try {
            DataBaseUtils.runQuery(DELETE_HISTORY + couponId);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * in order to avoid duplicated code (usages- delete coupon purchase, add coupon purchase)
     *
     * @param customerId an Integer that indicates the customer's id
     * @param couponId   an Integer that indicates the coupon's id
     * @return a collection to the statement in order to insert or delete a records by specific values
     */
    private static Map<Integer, Object> setParamsToMap(int customerId, int couponId) {
        Map<Integer, Object> fields = new HashMap<>();
        fields.put(1, customerId);
        fields.put(2, couponId);
        return fields;
    }
}

