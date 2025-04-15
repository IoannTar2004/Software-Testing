package org.example.mailRu;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MailRuInAccountTests {

    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:/Users/iwant/AppData/Local/Google/Chrome/User Data");
        options.addArguments("--profile-directory=Default");

        Configuration.browser = "chrome";
        Configuration.screenshots = true;
        Configuration.browserCapabilities = options;
        Configuration.timeout = 10000;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                    .screenshots(true));
    }

    @BeforeEach
    void openPage() {
        open("https://e.mail.ru/inbox");
    }


    private void sendToMyself() {
        $x("//span[text()='Написать письмо']").click();
        $x("//div[contains(@class, 'container--hmD9c')]//input").setValue("iwan.tarasow2004.st@mail.ru");
        $x("//div[contains(@class, 'container--3QXHv')]//input").setValue("Тестовая тема");
        $x("//div[@contenteditable='true']").setValue("Текст");
//        driverUtils.clickButton(SPAN, "Отправить");
        $x("//span[text()='Отправить']").click();
        Selenide.refresh();
    }

    @Test
    void sendReceiveAndDeleteMailTest() {
        sendToMyself();

        $x("//div[text()='Письма себе']").click();
        $x("//span[text()='Тестовая тема']").click();
        $x("//div[text()='Удалить']").click();

        $x("//div[text()='Корзина']").click();
        $x("//span[text()='Выделить все']").click();
        $x("//div[text()='Удалить']").click();

        $x("//div[contains(@class, 'layer-window')]//div[text()='Очистить']").click();
    }

    @Test
    void draftsTest() {
        $x("//span[text()='Написать письмо']").click();
        $x("//div[contains(@class, 'container--hmD9c')]//input").setValue("iwan.tarasow2004.st@mail.ru");
        $x("//span[text()='Сохранить']").click();
        Selenide.refresh();
        $x("//div[text()='Черновики']").click();
        $x("//span[text()='iwan.tarasow2004.st@mail.ru']").should(visible);
        $x("//span[text()='<Без темы>']").should(visible);
        $x("//span[text()='Выделить все']");
        $x("//div[text()='Удалить']");
    }

    @Test
    void spamTest() {
        sendToMyself();
        $x("//div[text()='Письма себе']").click();
        $x("//span[text()='Тестовая тема']").click();
        $x("//div[contains(@class, 'button2__txt') and text()='Спам']").click();

        $x("//div[text()='Спам']").click();
        $x("//span[text()='Иван Тарасов']").should(visible);
        $x("//span[text()='Тестовая тема']").should(visible);

        $x("//span[text()='Выделить все']");
        $x("//div[text()='Удалить']");
    }

    @Test
    void searchMailTest() {
//        throw new RuntimeException();
        sendToMyself();
        $x("//span[text()='Поиск по почте']").click();
        $x("//input[contains(@class, 'mail-operands_dynamic-input__input--Ckq58')]").setValue("Тестовая тема");
        $x("//span[text()='Найти']").click();
        $x("//span[text()='Иван Тарасов']").should(visible);
        $x("//span[text()='Тестовая тема']").should(visible);
    }

}
