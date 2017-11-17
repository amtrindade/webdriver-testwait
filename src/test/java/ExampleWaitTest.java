import org.junit.After;
import org.junit.Assert;
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

import com.google.common.base.Function;

import static org.junit.Assert.assertEquals;

public class ExampleWaitTest {

    private WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.firefox.marionette", "/home/atrindade/dev/drivers/geckodriver");
        driver = new FirefoxDriver();
        driver.get("http://www.google.com");
    }

    @Test
    public void testExplicitWait() {
        By locator = By.xpath("//a[text()='Gmail']");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
        element.click();
        assertEquals("Gmail - Free Storage and Email from Google", driver.getTitle());
    }

    @Test
    public void testFluentWait(){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {

            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(By.xpath("//a[text()='Gmail']"));
            }
        });

        element.click();
        assertEquals("Gmail - Free Storage and Email from Google", driver.getTitle());
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
//https://www.testingexcellence.com/webdriver-implicit-explicit-and-fluent-wait-examples/
//https://www.youtube.com/watch?v=_ekETqGEVKA