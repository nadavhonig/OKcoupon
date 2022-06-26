package Exceptions;

public enum LoginErrMsg {

    FAILED_ACCESS("couldn't log in password or email incorrect");

    private String msg;

    LoginErrMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
