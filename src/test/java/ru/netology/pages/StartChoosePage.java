package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.visible;

public class StartChoosePage {
    private SelenideElement heading = $$("h2").find(Condition.text("Путешествие дня"));
    private SelenideElement cashButton = $$("button").find(exactText("Купить"));
    private SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));

    public StartChoosePage() {
        heading.shouldBe(visible);
    }

    public PaymentPage goToPaymentPage() {
        cashButton.click();
        return new PaymentPage();
    }

    public CreditPage goToCreditPage() {
        creditButton.click();
        return new CreditPage();
    }
}
