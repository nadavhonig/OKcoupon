package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtils {
    /**
     * convert date from LocalDate JAVA to Date of mySQL
     *
     * @param localDate LocalDate is an immutable date-time object that represents a date, often viewed as year-month-day
     * @return Date of mySQL
     */
    public static Date localDateToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    /**
     * @return start-date of coupon
     */
    public static LocalDate getStartDate() {
        return LocalDate.now().minusDays((int) (Math.random() * 14) + 1);
    }

    /**
     * @return end-date of coupon
     */
    public static LocalDate getEndDate() {
        return LocalDate.now().plusDays((int) (Math.random() * 14) + 1);
    }
    //todo: we don't use it anywhere!
    /**
     * gets a LocalDate and beautify its view
     *
     * @param localDate LocalDate is an immutable date-time object that represents a date, often viewed as year-month-day
     * @return date by format example (01/01/2022)
     */
    public static String beautifyLocalDate(LocalDate localDate) {
        return String.format("%02d/%02d/%04d",
                localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }

    /**
     * gets a LocalDate and beautify its view
     *
     * @param localDate LocalDate is an immutable date-time object that represents a date, often viewed as year-month-day
     * @return date by format example (01/01/2022/00:00:00)
     */
    public static String beautifyDateTime(LocalDateTime localDate) {
        return String.format("%02d/%02d/%04d %02d:%02d:%02d",
                localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear(),
                localDate.getHour(), localDate.getMinute(), localDate.getSecond()
        );
    }

    /**
     * gets a LocalDate and beautify its view with []
     *
     * @return example [01/01/2022/00:00:00]
     */
    public static String getLocalDateTime() {
        return "[" + beautifyDateTime(LocalDateTime.now()) + "]";
    }
}
