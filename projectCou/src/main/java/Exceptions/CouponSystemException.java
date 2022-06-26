package Exceptions;

public class CouponSystemException extends Exception {

    public CouponSystemException(String msg) {
        super(msg);
    }

    public CouponSystemException(Exceptions.CompanyErrMsg errors) {
        super(errors.getMsg());
    }

    public CouponSystemException(Exceptions.CouponErrMsg errors) {
        super(errors.getMsg());
    }

    public CouponSystemException(Exceptions.CustomerErrMsg errors) {
        super(errors.getMsg());
    }

    public CouponSystemException(Exceptions.LoginErrMsg errors) {
        super(errors.getMsg());
    }
}
