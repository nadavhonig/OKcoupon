package Exceptions;

public enum CompanyErrMsg {

    UPDATE_ID("can't update company id"),
    UPDATE_NAME("can't update company name"),
    NOT_EXISTS("company not exist"),
    DUPLICATE_EMAIL("email already exists"),
    DUPLICATE_NAME("name already exists"),
    DUPLICATE("company already exists"),
    INVALID_EMAIL("email is invalid");

    private String msg;

    CompanyErrMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
