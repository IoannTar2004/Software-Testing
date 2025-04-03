package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebDriverUtils {
    private WebDriver driver;

    public WebDriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public void clickButton(Tags tag, String text) {
        driver.findElement(By.xpath(String.format("//%s[text()='%s']", tag, text))).click();
    }

    public void assertText(Tags tag, String text) {
        driver.findElement(By.xpath(String.format("//%s[text()='%s']", tag, text)));
    }
    
    public WebElement getByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }
}
