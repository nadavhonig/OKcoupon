package beans;

import Exceptions.CouponErrMsg;
import Exceptions.CouponSystemException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    private int id;
    private int companyId;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * this method sets the id only if this id has not been initialized
     * and a user can not set the id to zero
     * create only once when the object initialize
     *
     * @param id an unique and auto-increment Integer from data-base
     * @throws CouponSystemException "can't update coupon id"
     */
    public final void setId(int id) throws CouponSystemException {
        if (this.id == 0 && id != 0) {
            this.id = id;
        } else throw new CouponSystemException(CouponErrMsg.UPDATE_ID);
    }

    @Override
    public String toString() {
        return "Title: " + title + " ID " + id +"";
    }
}
