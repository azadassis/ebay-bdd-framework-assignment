package pages;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

public class AddTocart {

    private static final Logger log = LoggerFactory.getLogger(AddTocart.class);
    private final WebDriver driver;
    int numberOfBooks;

    public AddTocart(WebDriver driver) {
        this.driver = driver;
    }

    public void addToCart(int numberOfBooks) {
        //Saving the parent window reference
        this.numberOfBooks = numberOfBooks;
        String parentWindow = driver.getWindowHandle();
        for (int i = 1; i <= numberOfBooks; i++) {
            String bookXpath = "(//img[@class='s-card__image'])[" + i + "]";
            WebElement element = driver.findElement(By.xpath(bookXpath));
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            driver.findElement(By.xpath(bookXpath)).click();
            //Waiting for new window to open and switching the driver control to new window
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(driver -> driver.getWindowHandles().size() > 1);
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                if (!window.equals(parentWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.id("atcBtn_btn_1"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Added to cart')]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Added to cart')]/parent::div/parent::h2/following-sibling::button"))).click();
            driver.close();
            driver.switchTo().window(parentWindow);
        }
    }

    public void checkAddToCart() throws IOException {
        driver.findElement(By.className("gh-cart__icon")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        String text = driver.findElement(By.xpath("//span[@class='gh-cart__icon']//span")).getText();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("gh-cart__icon")));
        Assert.assertEquals("Number of items in the cart not matching with the desired number of items", Integer.parseInt(text), numberOfBooks);
        log.info("Number of items in the cart verified to be {}", text);

        //Evidence
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("target/screenshots//page.png"));
        log.info("Refer target folder for screenshot evidence");
    }
}
