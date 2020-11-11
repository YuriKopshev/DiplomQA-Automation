package ru.netology.data;

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
}