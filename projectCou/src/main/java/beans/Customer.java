package beans;

import Exceptions.CouponSystemException;
import Exceptions.CustomerErrMsg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.ConsoleColors;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    /**
     * this method sets the email and checks if email contains '@'
     *
     * @param email customer's email
     * @throws CouponSystemException "email is invalid"
     */
    public void setEmail(String email) throws CouponSystemException {
        if (!email.contains("@")) {
            throw new CouponSystemException(CustomerErrMsg.INVALID_EMAIL);
        }
        this.email = email;
    }

    /**
     * this method sets the id only if this id has not been initialized
     * and a user can not set the id to zero
     * create only once when the object initialize
     *
     * @param id an unique and auto-increment Integer from data-base
     * @throws CouponSystemException "can't update customer id"
     */
    public final void setId(int id) throws CouponSystemException {
        if (this.id == 0 && id != 0) {
            this.id = id;
        } else throw new CouponSystemException(CustomerErrMsg.UPDATE_ID);
    }

    @Override
    public String toString() {
        return ConsoleColors.BLUE_BOLD+
                "Customer id: " +  ConsoleColors.RESET + id +" || " +
                ConsoleColors.BLUE_BOLD+ " firs name: " + ConsoleColors.RESET + firstName + " || "+
                ConsoleColors.BLUE_BOLD+ " last name: " + ConsoleColors.RESET + lastName + " || "+
                ConsoleColors.BLUE_BOLD+ " email: " + ConsoleColors.RESET + email + " || "+
                ConsoleColors.BLUE_BOLD+ " password: " + ConsoleColors.RESET + password  + " || "+
                ConsoleColors.BLUE_BOLD+ "\ncoupons ---> " + ConsoleColors.RESET +
                coupons+"\n";
    }
}

