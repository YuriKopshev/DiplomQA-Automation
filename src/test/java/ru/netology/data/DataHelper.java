package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    public static Card getValidCard() {
        return new Card("4444 4444 4444 4441", "11", "22", "Petrov Vladimir", "123");
    }

    public static Card getDeclinedCard() {
        return new Card("4444 4444 4444 4442", "11", "22", "Petrov Vladimir", "123");
    }

    public static Card getNotExistedCard() {
        return new Card("4444 4444 4444 4449", "11", "22", "Petrov Vladimir", "123");
    }

    public static Card getNotValidCard() {
        return new Card("4444 4444 4444 444", "11", "22", "Petrov Vladimir", "123");
    }

    public static Card getExpiredMonthCard() {
        return new Card("4444 4444 4444 4441", "10", "20", "Petrov Vladimir", "123");
    }

    public static Card getExpiredYearCard() {
        return new Card("4444 4444 4444 4441", "11", "19", "Petrov Vladimir", "123");
    }

    public static Card getExceedYearCard() {
        return new Card("4444 4444 4444 4441", "11", "26", "Petrov Vladimir", "123");
    }

    public static Card getWithoutNumberCard() {
        return new Card(" ", "11", "22", "Petrov Vladimir", "123");
    }


    // only for CreditPage testing
    public static Card getExpirationDateLessOneYearCard() {
        return new Card("4444 4444 4444 4441", "11", "20", "Petrov Vladimir", "123");
    }

    public static Card getNullMonthCard() {
        return new Card("4444 4444 4444 4441", "00", "21", "Petrov Vladimir", "123");
    }

    public static Card getNotExistedMonthCard() {
        return new Card("4444 4444 4444 4441", "13", "22", "Petrov Vladimir", "123");
    }

    public static Card getEmptyFieldCard() {
        return new Card("", "", "", "", "");
    }

    public static Card getRusNameOwnerCard() {
        return new Card("4444 4444 4444 4441", "11", "22", "Сидоров Николай", "123");
    }

    public static Card getNotValidName() {
        return new Card("4444 4444 4444 4441", "11", "22", "123@#$%^", "123");
    }

    public static Card getNotValidCVCCard() {
        return new Card("4444 4444 4444 4441", "11", "22", "Petrov Vladimir", "12");
    }


    public static void clearTables() {
        val cleanCreditRequest = "DELETE FROM credit_request_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        val cleanPayment = "DELETE FROM payment_entity;";
        val runner = new QueryRunner();

        try (val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass")) {
            runner.update(conn, cleanCreditRequest);
            runner.update(conn, cleanOrder);
            runner.update(conn, cleanPayment);
        } catch (Exception e) {
            System.out.println("SQL exception in clearTables");
        }

    }

    public static String findPaymentStatus() {
        String status = "";
        val codesSQL = "SELECT status FROM payment_entity;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app",
                "app", "pass")) {
            status = runner.query(conn, codesSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return status;
    }

    public static String findCreditRequestStatus() {
        String status = "";
        val codesSQL = "SELECT status FROM credit_request_entity;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app",
                "app", "pass")) {
            status = runner.query(conn, codesSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return status;
    }


    public static String findCountOrderEntity() {
        Long count = null;
        val codesSQL = " SELECT COUNT(*) FROM order_entity;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app",
                "app", "pass")) {
            count = runner.query(conn, codesSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return Long.toString(count);
    }
}