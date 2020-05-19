package base.pages;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class ProductPage extends Page {

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public WebDriver getDriver() {
        return null;
    }

    @FindBy(xpath = "//*[text()='Matte']")
    public WebElement matteButton;

    @FindBy(xpath = "//*[text()='Gloss']")
    public WebElement glossButton;

    @FindBy(xpath = "//*[text()='Select frame:']/../../label")
    public List<WebElement> frameList;

    @FindBy(xpath = "//div[@class='add-to-cart-button-container']/button")
    public WebElement addToCartButton;

    @FindBy(xpath = "//div[@class='add-to-cart-button-container']/button/span")
    public WebElement addToCartSpan;

    @FindBy(xpath = "//*[text()='Proceed to cart']")
    public WebElement proceedToCartButton;

    public ProductPage selectFinish(finish finish) {
        switch (finish) {
            case MATTE:
                safeClick(matteButton, driver);
                break;
            case GLOSS:
                safeClick(glossButton, driver);
                break;
        }
        return this;
    }

    public ProductPage selectFrame(int frameNumber) {
        safeClick(frameList.get(frameNumber), driver);
        return this;
    }

    public ProductPage clickAddToCartButton() {
        safeClick(addToCartButton, driver);
        return this;
    }

    public String getDisplateId() {
        return driver.getCurrentUrl().substring(30);
    }

    public String getPrice() {
        await().atMost(10, TimeUnit.SECONDS).until(() -> addToCartSpan.getText().matches(".*\\d.*"));
        String[] label = StringUtils.split(addToCartSpan.getText(), " ");
        return label[0];
    }

    public Cart clickProceedToCartButton() {
        safeClick(proceedToCartButton, driver);
        return new Cart(driver);
    }

    public enum finish {
        MATTE,
        GLOSS
    }
}
