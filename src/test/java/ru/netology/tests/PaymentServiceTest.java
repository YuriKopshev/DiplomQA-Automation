package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlUtils;
import ru.netology.pages.StartChoosePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentServiceTest {
    @BeforeEach
    public void openPage() {
        String url = "http://localhost:8080";
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SqlUtils.clearTables();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    /*positive scenarios
     */
    @Test
    @DisplayName("Покупка по карте,операция прошла успешно,в БД появилась запись со статусом APPROVED")
    void shouldConfirmPaymentWithValidCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getValidCard());
        payment.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SqlUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Покупка по карте,операция отклонена банком,в БД появилась запись со статусом DECLINED")
    void shouldDeniedPaymentWithDeclinedCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("DECLINED", SqlUtils.findPaymentStatus());

    }

    /*
    negative scenarios
     */

    @Test
    @DisplayName("Покупка по несуществующей карте,операция отклонена банком, в БД запись отсутствует")
    void shouldPaymentNonExistentCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotExistedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по невалидной карте,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNonValidCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotValidCard());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c истекшим сроком по месяцу,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldPaymentInvalidDateMonthCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getExpiredMonthCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c истекшим сроком по году,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldPaymentInvalidDateYearCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getExpiredYearCard());
        payment.waitNotificationExpiredErrorVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c годом превышающим 5 лет,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldPaymentExceedYearCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getExceedYearCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c пустым полем Номер карты,появилось сообщение «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void shouldPaymentEmptyNumberCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getWithoutNumberCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка в кредит по карте со сроком истечения менее года,появилось сообщение «Неверно указан срок действия карты», " +
            "в БД запись отсутствует")
    void shouldCreditPayExpirationDateLessOneYearCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToCreditPage();
        payment.putData(DataHelper.getExpirationDateLessOneYearCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте со значением 00 в поле месяц ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNullMonthCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNullMonthCard());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с невалидным значением месяца ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNotExistedMonthCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotExistedMonthCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте c пустыми полями,появились 4 сообщения \"Неверный формат\" и 1 - «Поле обязательно для заполнения», " +
            "в БД запись отсутствует")
    void shouldPaymentEmptyFieldCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getEmptyFieldCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с кириллицей в поле Владелец,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentRusNameOwnerCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getRusNameOwnerCard());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с символами в поле Владелец,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNotValidNameCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotValidName());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }

    @Test
    @DisplayName("Покупка по карте с невалидными данными в поле CVC/CVV  ,появилось сообщение «Неверный формат», " +
            "в БД запись отсутствует")
    void shouldPaymentNotValidCVCCard() {
        val startPage = new StartChoosePage();
        val payment = startPage.goToPaymentPage();
        payment.putData(DataHelper.getNotValidCVCCard());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SqlUtils.findCountOrderEntity());
    }
}
