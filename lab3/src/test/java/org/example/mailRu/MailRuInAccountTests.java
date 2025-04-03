package org.example.mailRu;

import org.example.utils.WebDriverUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static org.example.utils.Tags.*;

public class MailRuInAccountTests {

    static WebDriver driver;
    static WebDriverUtils driverUtils;

    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:/Users/iwant/AppData/Local/Google/Chrome/User Data");
        options.addArguments("--profile-directory=Default");
        driver = new ChromeDriver(options);
        driverUtils = new WebDriverUtils(driver);
    }

    @AfterAll
    static void tearDown() {
        driver.close();
        driver.quit();
    }

    @BeforeEach
    void open() {
        driver.get("https://e.mail.ru/inbox");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        driver.manage().window().maximize();
    }

    private void sendToMyself() {
        driverUtils.clickButton(SPAN, "Написать письмо");
        driver.findElement(By.xpath("//div[contains(@class, 'container--hmD9c')]//input"))
                .sendKeys("iwan.tarasow2013@mail.ru");
        driver.findElement(By.xpath("//div[contains(@class, 'container--3QXHv')]//input"))
                .sendKeys("Тестовая тема");
        driverUtils.clickButton(SPAN, "Отправить");
        driver.findElements(By.xpath("//span[text()='Отправить']")).get(1).click();
        driver.navigate().refresh();
    }

    @Test
    void sendReceiveAndDeleteMailTest() {
        sendToMyself();

        driverUtils.clickButton(DIV, "Письма себе");
        driverUtils.assertText(SPAN, "Иван Тарасов");
        driverUtils.clickButton(SPAN, "Тестовая тема");
        driverUtils.clickButton(DIV, "Удалить");

        driverUtils.clickButton(DIV, "Корзина");
        driverUtils.clickButton(SPAN, "Выделить все");
        driverUtils.clickButton(DIV, "Удалить");

        driver.findElement(By.xpath("//div[contains(@class, 'layer-window')]//div[text()='Очистить']")).click();
    }

    @Test
    void draftsTest() {
        driverUtils.clickButton(SPAN, "Написать письмо");
        driver.findElement(By.xpath("//div[contains(@class, 'container--hmD9c')]//input"))
                .sendKeys("iwan.tarasow2013@mail.ru");
        driverUtils.clickButton(SPAN, "Сохранить");
        driver.navigate().refresh();
        driverUtils.clickButton(DIV, "Черновики");
        driverUtils.assertText(SPAN, "iwan.tarasow2013@mail.ru");
        driverUtils.assertText(SPAN, "<Без темы>");
        driverUtils.clickButton(SPAN, "Выделить все");
        driverUtils.clickButton(DIV, "Удалить");
    }

    @Test
    void spamTest() {
        sendToMyself();
        driverUtils.clickButton(DIV, "Письма себе");
        driverUtils.clickButton(SPAN, "Иван Тарасов");
        driverUtils.findByXpath("//div[contains(@class, 'button2__txt') and text()='Спам']").click();

        driverUtils.clickButton(DIV, "Спам");
        driverUtils.clickButton(SPAN, "Открыть");
        driverUtils.assertText(SPAN, "Иван Тарасов");
        driverUtils.assertText(SPAN, "Тестовая тема");
        driverUtils.clickButton(SPAN, "Выделить все");
        driverUtils.clickButton(DIV, "Удалить");
    }

    @Test
    void searchMailTest() {
        sendToMyself();
        driverUtils.clickButton(SPAN, "Поиск по почте");
        driverUtils.findByXpath("//input[contains(@class, 'mail-operands_dynamic-input__input--Ckq58')]")
                .sendKeys("Тестовая тема");
        driverUtils.clickButton(SPAN, "Найти");
        driverUtils.assertText(SPAN, "Иван Тарасов");
        driverUtils.assertText(SPAN, "Тестовая тема");
    }
}
