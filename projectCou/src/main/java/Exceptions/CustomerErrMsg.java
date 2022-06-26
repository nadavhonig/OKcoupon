package Exceptions;

public enum CustomerErrMsg {

    UPDATE_ID("can't update customer id"),
    NO_EXISTS("customer not exist"),
    DUPLICATE_MAIL("email already exists"),
    DUPLICATE("customer already exists"),
    INVALID_EMAIL("email is invalid");

    private String msg;

    CustomerErrMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
