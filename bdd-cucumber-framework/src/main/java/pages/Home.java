package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Home {

    private final WebDriver driver;

    public Home(WebDriver driver) {
        this.driver = driver;
    }

    public void search(String keyword) {
        //Implicit wait for the website to fully load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-search-btn")));
        driver.findElement(By.id("gh-ac")).sendKeys(keyword, Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//img[@class='s-card__image'])[1]")));
    }
}
