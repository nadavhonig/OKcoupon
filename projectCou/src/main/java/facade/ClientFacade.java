
package facade;

import Exceptions.CouponSystemException;

public abstract class ClientFacade {
    public abstract boolean login(String email, String password) throws CouponSystemException;
}
