package thread;

import Exceptions.CouponSystemException;
import beans.Coupon;
import dbdao.CouponsDBDAO;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
    private boolean quit = false;

    public CouponExpirationDailyJob() {
    }


    @Override
    public void run() {
        while (!quit) {
            List<Coupon> couponList = couponsDBDAO.getAllCoupons();
            try {
                for (Coupon item : couponList) {
                    if (DateUtils.localDateToSqlDate(LocalDate.now()).after(item.getEndDate())) {
                        couponsDBDAO.deleteCoupon(item.getId());
                        couponsDBDAO.deleteHistoryOfCoupons(item.getId());
                    }
                }
            } catch (CouponSystemException err) {
                System.out.println(err.getMessage());
            } finally {
                try {
                    Thread.sleep(24 * 60 * 60 * 1000);
                } catch (InterruptedException err) {
                    System.out.println(err.getMessage());
                    stop();
                }
            }
        }
    }

    /**
     * interrupt the thread and stop the coupon expiration daily job
     */
    public void stop() {
        System.out.println("coupon expiration scan is about to pause");
        this.quit = true;
    }
}
