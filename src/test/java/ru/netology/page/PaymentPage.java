package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement heading = $$("h3").find(exactText("Оплата по карте"));
    private SelenideElement cardField = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement monthField = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private SelenideElement yearField = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private SelenideElement CVCField = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");


    private SelenideElement successfulOperation = $$("[class=\"notification__content\"]").find(text("Операция одобрена Банком."));
    private SelenideElement failedOperation = $$("[class=\"notification__content\"]").find(text("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private ElementsCollection wrongFormatFourError = $$(byText("Неверный формат"));
    private SelenideElement validityError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));
    private SelenideElement fieldRequiredError = $(byText("Поле обязательно для заполнения"));

    private SelenideElement cancelSuccessField = $$("[class=\"icon-button__text\"]").first();
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));


    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void putData(Card card) {
        cardField.setValue(card.getCardNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        ownerField.setValue(card.getOwner());
        CVCField.setValue(card.getCvc());
        continueButton.click();
    }

    public void notificationSuccessVisible() {
        successfulOperation.waitUntil(visible,10000);
        cancelSuccessField.click();
    }

    public void notificationFailedVisible() {
        failedOperation.waitUntil(visible,10000);
    }

    public void notificationWrongFormatVisible() {
        wrongFormatError.waitUntil(visible,10000);
    }

    public void notificationValidityErrorVisible() {
        validityError.waitUntil(visible,10000);
    }

    public void notificationExpiredErrorVisible() {
        cardExpiredError.waitUntil(visible,10000);
    }

    public void notificationRequiredFieldVisible() {
        fieldRequiredError.waitUntil(visible,10000);
    }

    public void notificationFullWrongFormatVisible() {
     wrongFormatFourError.shouldHaveSize(4);
     fieldRequiredError.waitUntil(visible,10000);
    }




}