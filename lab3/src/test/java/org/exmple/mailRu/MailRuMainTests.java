package org.exmple.mailRu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MailRuMainTests {
    static WebDriver driver;

    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        driver.close();
        driver.quit();
    }

    @BeforeEach
    void open() {
        driver.get("https://mail.ru/");
    }

    @Test
    public void yandexSearchTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//input[@name='text']")).sendKeys("пробки");
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//span[contains(normalize-space(), 'Яндекс Карты')]"));
    }

    @Test
    public void loginTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1054, 896));
        driver.findElement(By.xpath("//button[contains(., 'Войти')]")).click();
        driver.switchTo().frame(5);
        driver.findElement(By.xpath("//span[contains(@class, 'ProvidersListItemIconYandex')]")).click();
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("iwan.tarasow2013");
        driver.findElement(By.xpath("//span[contains(., 'Продолжить')]")).click();
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[contains(@class, 'Button2')]")).click();
        driver.findElement(By.xpath("//input[@placeholder='Введите пароль']")).sendKeys("121212");
        driver.findElement(By.xpath("//button[contains(@class, 'Button2_type_submit')]")).click();
        driver.findElement(By.xpath("//div[contains(., 'Неверный пароль')]"));
    }

    @Test
    public void createMailTest() {
        driver.get("https://mail.ru/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().setSize(new Dimension(1054, 896));
        driver.findElement(By.linkText("Создать почту")).click();
        Set<String> windowHandles = driver.getWindowHandles();
        driver.switchTo().window(new ArrayList<>(windowHandles).get(1));
        driver.findElement(By.xpath("//input[@name='fname']")).sendKeys("Иван");
        driver.findElement(By.xpath("//input[@name='lname']")).sendKeys("Тарасов");
        driver.findElement(By.xpath("//span[contains(., 'День')]")).click();
        driver.findElement(By.xpath("//span[contains(., 'Месяц')]")).click();
        driver.findElement(By.xpath("//span[contains(., 'Год')]")).click();
        driver.findElement(By.cssSelector(".label-0-2-137:nth-child(1) .base-0-2-61")).click();
        driver.findElement(By.xpath("//input[@name='partial_login']")).sendKeys("dhbfhebfiebffeiub");
        driver.findElement(By.id("phone-number__phone-input")).sendKeys("9818391598");
        driver.findElement(By.xpath("//button[contains(@type, submit)]")).click();
    }

//    @Test
//    public void newsTest(){
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.manage().window().maximize();
//        driver.findElement(By.xpath("//a[contains(@class, 'news-main')]")).click();
//
//        Set<String> windowHandles = driver.getWindowHandles();
//        driver.switchTo().window(new ArrayList<>(windowHandles).get(1));
//        driver.findElement(By.xpath("//span[contains(., 'Новости')]")).getText();
//        driver.close();
//    }

    @Test
    public void darkThemeTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://mail.ru/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".ph-settings-container")).click();
        driver.findElement(By.cssSelector(".ph-theme-choice__item:nth-child(2) .StylishRadioButton")).click();
        assertEquals("rgba(25, 25, 26, 1)", driver.findElement(By.tagName("body"))
                .getCssValue("background-color"));

        driver.findElement(By.cssSelector(".ph-settings-container")).click();
        driver.findElement(By.cssSelector(".ph-theme-choice__item:nth-child(3) .StylishRadioButton")).click();
        System.out.println(driver.findElement(By.tagName("body")).getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", driver.findElement(By.tagName("body"))
                .getCssValue("background-color"));
    }
}
