
package login;

import Exceptions.CouponSystemException;
import Exceptions.LoginErrMsg;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import utils.DateUtils;

public class LoginManager {
    /**
     * making sure we use the same instance of LoginManager
     */
    private static LoginManager instance = null;

    private LoginManager() {
    }

    /**
     * manage the logins to facades
     *
     * @return a singleton class
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     * @param email      unique email of the user
     * @param password   unique password of the user
     * @param clientType the type of the user- administrator, company, customer
     * @return the appropriate facade based on user logged in
     * @throws CouponSystemException "couldn't log in password or email incorrect"
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
        switch (clientType) {
            case Administrator:
                ClientFacade adminFacade = new AdminFacade();
                if (!adminFacade.login(email, password)) {
                    throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
                }
                System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
                return adminFacade;
            case Company:
                ClientFacade companyFacade = new CompanyFacade();
                if (!companyFacade.login(email, password)) {
                    throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
                }
                return companyFacade;

            case Customer:
                ClientFacade customerFacade = new CustomerFacade();
                if (!customerFacade.login(email, password)) {
                    throw new CouponSystemException(LoginErrMsg.FAILED_ACCESS);
                }
                return customerFacade;
        }
        return null;
    }
}

