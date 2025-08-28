package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.AddTocart;
import pages.Home;
import utils.DriverFactory;

import java.io.IOException;

public class EbaySearch {

    WebDriver driver;
    AddTocart addToCartPage;
    Home homePage;

    @Given("i am on the Ebay search page")
    public void iAmOnTheGoogleSearchPage() {
        driver = DriverFactory.getDriver();
        addToCartPage = new AddTocart(driver);
        homePage = new Home(driver);
        driver.get("https://www.ebay.com/");
    }

    @When("i search for {string}")
    public void iSearchFor(String keyword) throws InterruptedException {
        homePage.search(keyword);
    }

    @Then("the page title should start with {string}")
    public void thePageTitleShouldStartWith(String keyword) {
        //Validating the title card
        Assert.assertEquals("Title not matching", driver.getTitle(), keyword);
    }

    @And("add {int} to cart")
    public void addToCart(int numberOfBooks) {
        addToCartPage.addToCart(numberOfBooks);
    }

    @Then("i will check the cart for the items added")
    public void iWillCheckTheCartForTheItemsAdded() throws IOException {
        addToCartPage.checkAddToCart();
    }

}
