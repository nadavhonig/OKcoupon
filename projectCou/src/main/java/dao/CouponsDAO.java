package dao;

import Exceptions.CouponSystemException;
import beans.Coupon;

import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon) throws CouponSystemException;

    void updateCoupon(Coupon coupon);

    void deleteCoupon(int couponId) throws CouponSystemException;

    ArrayList<Coupon> getAllCoupons();

    Coupon getOneCoupon(int CouponId) throws CouponSystemException;

    boolean addCouponPurchase(int customerId, int couponId);

    void deleteCouponPurchase(int customerId, int couponId);
}
