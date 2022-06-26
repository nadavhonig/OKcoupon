package beans;

import Exceptions.CompanyErrMsg;
import Exceptions.CouponSystemException;
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
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    /**
     * this method sets the id only if this id has not been initialized
     * and a user can not set the id to zero
     * create only once when the object initialize
     *
     * @param id an unique and auto-increment Integer from mySQL data-base
     * @throws CouponSystemException "can't update company id"
     */
    public final void setId(int id) throws CouponSystemException {
        if (this.id == 0 && id != 0) {
            this.id = id;
        } else throw new CouponSystemException(CompanyErrMsg.UPDATE_ID);
    }

    /**
     * this method sets the email and checks if email contains '@'
     *
     * @param email company's email
     * @throws CouponSystemException "email is invalid"
     */
    public void setEmail(String email) throws CouponSystemException {
        if (!email.contains("@")) {
            throw new CouponSystemException(CompanyErrMsg.INVALID_EMAIL);
        }
        this.email = email;
    }

    /**
     * this method restricts the option to change a company name
     *
     * @param name company's name can not be changed
     * @throws CouponSystemException "can't update company name"
     */
    public void setName(String name) throws CouponSystemException {
        throw new CouponSystemException(CompanyErrMsg.UPDATE_NAME);
    }

    @Override
    public String toString() {


        return ConsoleColors.BLUE_BOLD+
                "Company id: " + ConsoleColors.RESET + id +" || "+
                ConsoleColors.BLUE_BOLD+  " name: " + ConsoleColors.RESET+ name +  " || "+
                ConsoleColors.BLUE_BOLD+  " email: " + ConsoleColors.RESET + email +  " || "+
                ConsoleColors.BLUE_BOLD+ " password: " + ConsoleColors.RESET + password + " || "+
                ConsoleColors.BLUE_BOLD+ "\ncoupons ---> " + ConsoleColors.RESET+
                coupons +"\n";

    }
}
