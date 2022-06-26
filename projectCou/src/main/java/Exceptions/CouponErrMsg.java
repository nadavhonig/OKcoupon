package Exceptions;

public enum CouponErrMsg {

    UPDATE_ID("can't update coupon id"),
    NOT_EXISTS("coupon not exist"),
    DUPLICATE("coupon already exists"),
    NO_COUPONS_LEFT("no coupons left"),
    EXPIRED("the date of coupon has passed"),
    PURCHASE_FAILED("can't purchase more of this coupon");

    private String msg;

    CouponErrMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    }
