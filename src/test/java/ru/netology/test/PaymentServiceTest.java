package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.StartChoosePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class PaymentServiceTest {

    @AfterEach
     public void cleanBase(){
        DataHelper.clearTables();
    }
   /*positive scenarios
    */
    @Test
    @DisplayName("Покупка по карте,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldConfirmPaymentWithValidCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getValidCard());
        payment.notificationSuccessVisible();
        assertEquals("APPROVED",DataHelper.findPaymentStatus());
    }

    @Test
    @DisplayName("Покупка в кредит,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldConfirmCreditPayWithValidCard() {
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getValidCard());
        payment.notificationSuccessVisible();
        assertEquals("APPROVED", DataHelper.findCreditRequestStatus());
    }


    @Test
    @DisplayName("Покупка по карте,операция отклонена банком,в БД появилась запись со статусом DECLINED")
    void shouldDeniedPaymentWithDeclinedCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getDeclinedCard());
        payment.notificationFailedVisible();
        assertEquals("DECLINED",DataHelper.findPaymentStatus());

    }


    @Test
    @DisplayName("Покупка в кредит,операция отклонена банком,в БД появилась запись со статусом DECLINED")
    void shouldDeniedCreditPayWithDeclinedCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getDeclinedCard());
        payment.notificationFailedVisible();
        assertEquals("DECLINED",DataHelper.findCreditRequestStatus());

    }

    /*
    negative scenarios
     */

    @Test
    @DisplayName("Покупка по несуществующей карте,операция отклонена банком, в БД запись отсутствует")
    void shouldPaymentNonExistentCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotExistedCard());
        payment.notificationFailedVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по несуществующей карте,операция отклонена банком, " +
            "в БД запись отсутствует")
    void shouldCreditPayNonExistentCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getNotExistedCard());
        payment.notificationFailedVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по невалидной карте,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNonValidCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotValidCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по невалидной карте в кредит,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldCreditPayNonValidCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getNotValidCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c истекшим сроком по месяцу,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldPaymentInvalidDateMonthCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getExpiredMonthCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте c истекшим сроком по месяцу,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldCreditPayInvalidDateMonthCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getExpiredMonthCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c истекшим сроком по году,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldPaymentInvalidDateYearCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getExpiredYearCard());
        payment.notificationExpiredErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте c истекшим сроком по году,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldCreditPayInvalidDateYearCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getExpiredYearCard());
        payment.notificationExpiredErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c годом превышающим 5 лет,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldPaymentExceedYearCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getExceedYearCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте c годом превышающим 5 лет,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldCreditPayExceedYearCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getExceedYearCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем Номер карты,появилось сообщение «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void shouldPaymentEmptyNumberCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getWithoutNumberCard());
        payment.notificationRequiredFieldVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте c пустым полем Номер карты,появилось сообщение «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void shouldCreditPayEmptyNumberCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getWithoutNumberCard());
        payment.notificationRequiredFieldVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте со сроком истечения менее года,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldCreditPayExpirationDateLessOneYearCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getExpirationDateLessOneYearCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте со значением 00 в поле месяц ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNullMonthCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNullMonthCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте со значением 00 в поле месяц ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldCreditPayNullMonthCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getNullMonthCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с невалидным значением месяца ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNotExistedMonthCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotExistedMonthCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте с невалидным значением месяца ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldCreditPayNotExistedMonthCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getNotExistedMonthCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c пустыми полями,появились 4 сообщения \"Неверный формат\" и 1 - «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void shouldPaymentEmptyFieldCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getEmptyFieldCard());
        payment.notificationFullWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте c пустыми полями,появились 4 сообщения \"Неверный формат\" и 1 - «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void shouldCreditPayEmptyFieldCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getEmptyFieldCard());
        payment.notificationFullWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с кириллицей в поле Владелец,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentRusNameOwnerCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getRusNameOwnerCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка вкредит по карте с кириллицей в поле Владелец,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldCreditPayRusNameOwnerCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getRusNameOwnerCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с символами в поле Владелец,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNotValidNameCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotValidName());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте с символами в поле Владелец,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldCreditPayNotValidNameCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getNotValidName());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с невалидными данными в поле CVC/CVV  ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNotValidCVCCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotValidCVCCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте с невалидными данными в поле CVC/CVV  ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldCreditPayNotValidCVCCard(){
        open("http://localhost:8080");
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getNotValidCVCCard());
        payment.notificationWrongFormatVisible();
        assertEquals("0",DataHelper.findCountOrderEntity());
    }



























}
