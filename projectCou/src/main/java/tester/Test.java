package tester;

import Exceptions.CouponSystemException;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db_util.ConnectionPool;
import db_util.DataBaseManager;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import login.ClientType;
import login.LoginManager;
import thread.CouponExpirationDailyJob;
import utils.Art;
import utils.ConsoleColors;
import utils.DateUtils;
import utils.TablePrinter;


public class Test {
    /**
     * method to use to get a random number for amount of coupon
     *
     * @return a random Integer , range 5-24
     */
    public int amountSetter() {
        return (int) (Math.random() * 20 + 5);
    }

    /// companies:///
    public Company hyundai = Company.builder()
            .name("hyundai")
            .email("hyundai@gmail.com")
            .password("1234")
            .build();
    public Company gucci = Company.builder()
            .name("gucci")
            .email("gucci@gmail.com")
            .password("12345")
            .build();
    public Company ibm = Company.builder()
            .name("ibm")
            .email("ibm@gmail.com")
            .password("123456")
            .build();
    public Company facebook = Company.builder()
            .name("facebook")
            .email("facebook@gmail.com")
            .password("1234567")
            .build();
    public Company google = Company.builder()
            .name("google")
            .email("google@walla.com")
            .password("12345678")
            .build();

    ///coupons///

    public Coupon couponToDelete = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("couponToDelete")
            .description("delete delete delete")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon tvHouse = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("airpods")
            .description("airpods pro")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(487)
            .image(":)))")
            .amount(amountSetter())
            .build();


    public Coupon zarra = Coupon.builder()
            .category(Category.FASHION)
            .title("one+one")
            .description("buy one get the second hinam")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(100.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon sony = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("ps5")
            .description("controller coupon")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(280.)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon totto = Coupon.builder()
            .category(Category.RESTAURANT)
            .title(" desert coupon")
            .description("yam yam")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(80.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon skiDeal = Coupon.builder()
            .category(Category.VACATION)
            .title("ski trip")
            .description("eize keff")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(1000.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon cinemacity = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("vip lounge")
            .description("kama food")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(60.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon musicPerformanceClub = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("vip lounge")
            .description("vip accesses ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(125.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon tatooMania = Coupon.builder()
            .category(Category.FASHION)
            .title("one + one ")
            .description("second tatto is free ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(210.10)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon gymBoxer = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("free month ")
            .description("free month membership ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(30.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon hairForYou = Coupon.builder()
            .category(Category.FASHION)
            .title("hair cut")
            .description("free hir cut ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(20.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon led = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("30 meter led strip")
            .description(" buy 20 bubble lamp get 30 meter led for free")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(340.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon steakHouse = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("couple meat")
            .description("get 3 steaks 2 ribs 5 starters and 2 deserts ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(550.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon romanticVacation = Coupon.builder()
            .category(Category.VACATION)
            .title("vacation")
            .description(" romantic weakened in the nature ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(1000.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon vacationInTheCity = Coupon.builder()
            .category(Category.VACATION)
            .title("hotel in tel aviv")
            .description("weakened in Hilton hotel ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(2000.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon shoesMania = Coupon.builder()
            .category(Category.FASHION)
            .title("nike shoes")
            .description("second pair for free")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(100.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon electra = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("mini air condition")
            .description("air condition for small spaces")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(180.0)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon jimbori = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("jimbo")
            .description("5 entries to the jumbori")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(140)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon weRun = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title(" tennis lessons ")
            .description("5 tennis lessons")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(448)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon luxuryWatches = Coupon.builder()
            .category(Category.FASHION)
            .title(" Rollex ")
            .description("Rollex db321")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(32122)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon tripAdvice = Coupon.builder()
            .category(Category.VACATION)
            .title(" into the wild ")
            .description("3 days trip in the desert ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(1456)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon chocolateFactory = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("chocolate adventure")
            .description("tour on chocolate factory")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(118)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon wineAndFINE = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("wine fest")
            .description(" drink all day ")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(90)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon noaORmergi = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("noa or mergi")
            .description("2 vip tickets for Noa Kirel or Mergi concert")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(510)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon socks4U = Coupon.builder()
            .category(Category.FASHION)
            .title("socks for you")
            .description("5 pairs of socks in different colors")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(130)
            .image(":)))")
            .amount(amountSetter())
            .build();

    public Coupon noSecond = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("eyal ein sheni lo")
            .description("very very yumi food for a cute couple")
            .startDate(DateUtils.localDateToSqlDate(DateUtils.getStartDate()))
            .endDate(DateUtils.localDateToSqlDate(DateUtils.getEndDate()))
            .price(1200)
            .image(":)))")
            .amount(amountSetter())
            .build();

    ///customers///

    public Customer itzik = Customer.builder()
            .firstName("itzik")
            .lastName("seaman-tof")
            .email("itzik@gmail.com")
            .password("basar")
            .build();
    public Customer tomer = Customer.builder()
            .firstName("tomer")
            .lastName("shimony")
            .email("tomer@gmail.com")
            .password("abbale")
            .build();
    public Customer yuri = Customer.builder()
            .firstName("yuri")
            .lastName("roeeeei")
            .email("yuri@gmail.com")
            .password("roeeeeei")
            .build();
    public Customer barak = Customer.builder()
            .firstName("barak")
            .lastName("hamdani")
            .email("barak@gmail.com")
            .password("lightning")
            .build();
    public Customer asi = Customer.builder()
            .firstName("asi")
            .lastName("taragano")
            .email("asi@gmail.com")
            .password("luba")
            .build();

    /////////////////////////////////////////////////////////

    AdminFacade adminFacade;
    CompanyFacade companyFacade;
    CustomerFacade customerFacade;

    /**
     * method that gets 5 coupons and adds it to data-base
     *
     * @param coupon1 coupon-1
     * @param coupon2 coupon-2
     * @param coupon3 coupon-3
     * @param coupon4 coupon-4
     * @param coupon5 coupon-5
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "the date of coupon has passed" OR "coupon already exists"
     */
    private void addCoupons(Coupon coupon1, Coupon coupon2, Coupon coupon3, Coupon coupon4, Coupon coupon5) throws CouponSystemException {
        companyFacade.addCoupon(coupon1);
        companyFacade.addCoupon(coupon2);
        companyFacade.addCoupon(coupon3);
        companyFacade.addCoupon(coupon4);
        companyFacade.addCoupon(coupon5);

    }

    /**
     * method that operates 4 methods in companyFacade - getCompanyDetails, getCompanyCoupons, getCouponsByCategory, getCouponsByPrice
     *
     * @param category the category to filter by it the coupons returns
     * @param maxPrice the maximum price to filter by it the coupons returns
     * @throws CouponSystemException "couldn't log in password OR email incorrect" OR "company doesn't exist" OR "coupon not exists"
     */
    private void printAllShowMethodsInCompany(Category category, int maxPrice) throws CouponSystemException {
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "company details:" + ConsoleColors.RESET);
        System.out.println(companyFacade.getCompanyDetails());
        System.out.println("======================================");
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "company coupons:" + ConsoleColors.RESET);
        TablePrinter.print(companyFacade.getCompanyCoupons());
        System.out.println("======================================");
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "company coupons by category " + category + ":" + ConsoleColors.RESET);
        TablePrinter.print(companyFacade.getCouponByCategory(category));
        System.out.println("======================================");
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "company coupons by price " + maxPrice + ":" + ConsoleColors.RESET);
        TablePrinter.print(companyFacade.getCouponByMaxPrice(maxPrice));
        System.out.println("======================================");
    }

    /**
     * method that purchase 5 coupons and adds this history of purchase to data-base
     *
     * @param coupon1 coupon-1
     * @param coupon2 coupon-2
     * @param coupon3 coupon-3
     * @param coupon4 coupon-4
     * @param coupon5 coupon-5
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "the date of coupon has passed" OR "no coupons left" OR "coupon not exist" OR "can't purchase more of this coupon"
     */
    private void purchaseCoupons(Coupon coupon1, Coupon coupon2, Coupon coupon3, Coupon coupon4, Coupon coupon5) throws CouponSystemException {
        customerFacade.purchaseCoupon(coupon1);
        customerFacade.purchaseCoupon(coupon2);
        customerFacade.purchaseCoupon(coupon3);
        customerFacade.purchaseCoupon(coupon4);
        customerFacade.purchaseCoupon(coupon5);
    }

    /**
     * method that operates 4 methods in customerFacade - getCustomerDetails, getCustomerCoupons, getCustomerCouponsByCategory, getCustomerCouponsByPrice
     *
     * @param category the category to filter by it the coupons returns
     * @param maxPrice the maximum price to filter by it the coupons returns
     * @throws CouponSystemException "couldn't log in password or email incorrect" OR "customer doesn't exist" OR "coupon doesn't exist"
     */
    private void printAllShowMethodsInCustomer(Category category, double maxPrice) throws CouponSystemException {
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "customer details:" + ConsoleColors.RESET);
        System.out.println(customerFacade.getCustomerDetails());
        System.out.println("======================================");
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "customer coupons:" + ConsoleColors.RESET);
        TablePrinter.print(customerFacade.getCustomerCoupons());
        System.out.println("======================================");
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "customer coupons by price " + maxPrice + ":" + ConsoleColors.RESET);
        TablePrinter.print(customerFacade.getCustomerCouponsByPrice(maxPrice));
        System.out.println("======================================");
        System.out.println(ConsoleColors.GREEN_UNDERLINED + "customer coupons by category " + category + ":" + ConsoleColors.RESET);
        TablePrinter.print(customerFacade.getCustomerCouponsByCategory(category));
    }

    /**
     * operate all the program methods in addition to test the program,
     * handle the exceptions that might be thrown by surrounded the risky methods with try and catch
     */
    public void TestAll() {
        System.out.println(Art.okCoupon);
        DataBaseManager.createAllDataBase();
        CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
        Thread thread = new Thread(couponExpirationDailyJob);
        thread.start();

        /// admin facade: add(company,customer),update (hyundi company password, yuri name)
        try {
            System.out.println(ConsoleColors.BLUE_BOLD + "admin: " + ConsoleColors.RESET);
            adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Administrator);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            //addCompany(hyundai, gucci, ibm, facebook, google);
            adminFacade.addCompany(hyundai);
            adminFacade.addCompany(gucci);
            adminFacade.addCompany(ibm);
            adminFacade.addCompany(facebook);
            adminFacade.addCompany(google);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            // add customers(asi,barak,yuri,itzik,tomer)
            adminFacade.addCustomer(asi);
            adminFacade.addCustomer(barak);
            adminFacade.addCustomer(yuri);
            adminFacade.addCustomer(itzik);
            adminFacade.addCustomer(tomer);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        // update company(hyundai)
        try {
            hyundai.setPassword("change password");
            adminFacade.updateCompany(hyundai);
            System.out.println("password changed successfully");
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        // update customer( yuri)
        try {
            yuri.setFirstName("was yuri");
            adminFacade.updateCustomer(yuri);
            System.out.println("name changed successfully");
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ConsoleColors.CYAN_BOLD + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "COMPANIES:" + ConsoleColors.RESET);

        /// first company(all methods) update zarra description delete coupon id 6
        try {
            System.out.println(ConsoleColors.BLUE_BOLD + hyundai.getName() + ": " + ConsoleColors.RESET);
            companyFacade = (CompanyFacade) LoginManager.getInstance().login("hyundai@gmail.com", "change password", ClientType.Company);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            addCoupons(zarra, sony, totto, skiDeal, cinemacity);
            companyFacade.addCoupon(couponToDelete);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            zarra.setDescription("description changed");
            companyFacade.updateCoupon(zarra);
            System.out.println("coupon was updated");
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCompany(Category.RESTAURANT, 300);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }

        try {
            companyFacade.deleteCoupon(6);
            System.out.println("coupon deleted");
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        companyFacade.logOut();
        /// second company all methods except delete and update coupon
        try {
            System.out.println("================================");
            Company company = adminFacade.getOneCompany(2);
            System.out.println(ConsoleColors.BLUE_BOLD + company.getName() + ": " + ConsoleColors.RESET);
            companyFacade = (CompanyFacade) LoginManager.getInstance().login(company.getEmail(), company.getPassword(), ClientType.Company);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            addCoupons(weRun, noaORmergi, shoesMania, steakHouse, socks4U);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCompany(Category.FASHION, 150);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        companyFacade.logOut();
        /// third company all methods except delete and update coupon
        try {
            System.out.println("================================");
            Company company = adminFacade.getOneCompany(3);
            System.out.println(ConsoleColors.BLUE_BOLD + company.getName() + ": " + ConsoleColors.RESET);
            companyFacade = (CompanyFacade) LoginManager.getInstance().login(company.getEmail(), company.getPassword(), ClientType.Company);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            addCoupons(musicPerformanceClub, hairForYou, tripAdvice, noSecond, tvHouse);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCompany(Category.VACATION, 558);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        companyFacade.logOut();

        /// fourth company all methods except delete and update coupon
        try {
            System.out.println("================================");
            Company company = adminFacade.getOneCompany(4);
            System.out.println(ConsoleColors.BLUE_BOLD + company.getName() + ": " + ConsoleColors.RESET);
            companyFacade = (CompanyFacade) LoginManager.getInstance().login(company.getEmail(), company.getPassword(), ClientType.Company);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            addCoupons(tatooMania, gymBoxer, led, romanticVacation, chocolateFactory);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCompany(Category.ENTERTAINMENT, 200);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        companyFacade.logOut();

        /// fifth company all methods except delete and update coupon
        try {
            System.out.println("================================");
            System.out.println(ConsoleColors.BLUE_BOLD + "google: " + ConsoleColors.RESET);
            companyFacade = (CompanyFacade) LoginManager.getInstance().login("google@walla.com", "12345678", ClientType.Company);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            addCoupons(wineAndFINE, electra, luxuryWatches, jimbori, vacationInTheCity);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCompany(Category.ELECTRICITY, 700);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        companyFacade.logOut();


        System.out.println(ConsoleColors.CYAN_BOLD + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "CUSTOMERS:" + ConsoleColors.RESET);

        /// first customer:
        try {
            System.out.println("================================");
            System.out.println(ConsoleColors.BLUE_BOLD + "itzik:" + ConsoleColors.RESET);
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("itzik@gmail.com", "basar", ClientType.Customer);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            purchaseCoupons(electra, skiDeal, luxuryWatches, jimbori, wineAndFINE);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCustomer(Category.FASHION, 407);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        customerFacade.logOut();
        /// second customer:
        try {
            System.out.println("================================");
            System.out.println(ConsoleColors.BLUE_BOLD + "tomer:" + ConsoleColors.RESET);
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("tomer@gmail.com", "abbale", ClientType.Customer);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            purchaseCoupons(weRun, musicPerformanceClub, hairForYou, skiDeal, tvHouse);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCustomer(Category.ELECTRICITY, 1000.0);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        customerFacade.logOut();

        // third customer:
        try {
            System.out.println("================================");
            System.out.println(ConsoleColors.BLUE_BOLD + "yuri:" + ConsoleColors.RESET);
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("yuri@gmail.com", "roeeeeei", ClientType.Customer);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            purchaseCoupons(romanticVacation, tatooMania, steakHouse, noaORmergi, led);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCustomer(Category.VACATION, 1500);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        customerFacade.logOut();

        // fourth customer:
        try {
            System.out.println("================================");
            System.out.println(ConsoleColors.BLUE_BOLD + "barak:" + ConsoleColors.RESET);
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("barak@gmail.com", "lightning", ClientType.Customer);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            purchaseCoupons(shoesMania, noSecond, tripAdvice, cinemacity, led);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCustomer(Category.FASHION, 600);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        customerFacade.logOut();

        // fifth customer:
        try {
            System.out.println("================================");
            System.out.println(ConsoleColors.BLUE_BOLD + "asi:" + ConsoleColors.RESET);
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("asi@gmail.com", "luba", ClientType.Customer);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            purchaseCoupons(chocolateFactory, luxuryWatches, romanticVacation, jimbori, tvHouse);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            printAllShowMethodsInCustomer(Category.ELECTRICITY, 1000);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        customerFacade.logOut();

        /// show all companies,customers,one company, one customer
        System.out.println(ConsoleColors.CYAN_BOLD + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "ALL ADMIN PRINT METHODS:" + ConsoleColors.RESET);
        try {
            System.out.println(ConsoleColors.GREEN_UNDERLINED + "Get One Company" + ConsoleColors.RESET);
            System.out.println(adminFacade.getOneCompany(1));
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            System.out.println(ConsoleColors.GREEN_UNDERLINED + "Get One Customer" + ConsoleColors.RESET);
            System.out.println(adminFacade.getOneCustomer(1));
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            System.out.println(ConsoleColors.GREEN_UNDERLINED + "Get All Customers" + ConsoleColors.RESET);
            System.out.println(adminFacade.getAllCustomers());
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            System.out.println(ConsoleColors.GREEN_UNDERLINED + "Get All Companies" + ConsoleColors.RESET);
            System.out.println(adminFacade.getAllCompanies());
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }


        ///delete (customer,company)
        System.out.println(ConsoleColors.CYAN_BOLD + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "ALL ADMIN DELETE METHODS:" + ConsoleColors.RESET);
        try {
            System.out.println(ConsoleColors.GREEN_UNDERLINED + "Delete Company" + ConsoleColors.RESET);
            adminFacade.deleteCompany(5);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        try {
            System.out.println(ConsoleColors.GREEN_UNDERLINED + "Delete Customer" + ConsoleColors.RESET);
            adminFacade.deleteCustomer(5);
        } catch (CouponSystemException err) {
            System.out.println(err.getMessage());
        }
        adminFacade.logOut();


        System.out.println(ConsoleColors.CYAN_BOLD + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "================================" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "CLOSE ALL CONNECTIONS AND END DAILY JOB:" + ConsoleColors.RESET);

        thread.interrupt();
        /// close all connections
        System.out.println("Opened connections: " + ConnectionPool.getInstance().getOpenedConnections());
        try {
            ConnectionPool.getInstance().closeAllConnection();
            System.out.println("connection closed");
        } catch (InterruptedException err) {
            System.out.println(err.getMessage());
        }
        System.out.println(ConsoleColors.RED_BOLD + "test ended" + ConsoleColors.RESET);
        System.out.println(Art.credit);
    }
}