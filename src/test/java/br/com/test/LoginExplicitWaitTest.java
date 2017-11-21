package br.com.test;

import com.google.common.base.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class LoginExplicitWaitTest {

    private WebDriver driver;
    private WebElement passwd;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "/home/atrindade/dev/drivers/geckodriver");
        driver = new FirefoxDriver();
        driver.get("http://www.gmail.com");
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void loginExplicitWaitTest(){
        WebElement email = driver.findElement(By.id("identifierId"));
        email.sendKeys("email@gmail.com");
        WebElement next = driver.findElement(By.id("identifierNext"));
        next.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        passwd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        //passwd = driver.findElement(By.id("password"));
        assertTrue(passwd.isDisplayed());
    }

    @Test
    public void loginFluentWaitTest(){
        WebElement email = driver.findElement(By.id("identifierId"));
        email.sendKeys("email@gmail.com");
        WebElement next = driver.findElement(By.id("identifierNext"));
        next.click();

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        passwd = wait.until(new Function<WebDriver, WebElement>(){

            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("password"));
            }
        });
        passwd.sendKeys("senha");

    }
}
