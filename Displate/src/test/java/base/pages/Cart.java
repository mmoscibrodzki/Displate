package base.pages;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

public class Cart extends Page {

    WebDriver driver;

    public Cart(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @FindBy(xpath = "//a[@class='image-in-cart image-in-cart--frame']")
    public WebElement displateImageLink;

    @FindBy(xpath = "//div[@class='desk-cart-subtotal']/span[2]")
    public WebElement subtotalPrice;

    @FindBy(id = "select-country")
    public WebElement selectCountry;

    @FindBy(xpath = "//div[@class='simplebar-track vertical']/div[@class='simplebar-scrollbar visible']")
    public WebElement scrollbar;

    @FindBy(xpath = "//div[@class='discount']/div[1]")
    public WebElement iHaveADiscountCode;

    @FindBy(xpath = "//span[@id='order-total']/strong")
    public WebElement orderTotal;

    @FindBy(id = "discount-code")
    WebElement discountCode;

    @FindBy(id = "discount-apply")
    WebElement discountApply;

    public Cart selectCountry(String countryName) {
        selectCountry.click();
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(scrollbar, 0, 280).perform();
        driver.findElement(By.xpath("//div[text()='" + countryName + "']")).click();
        return this;
    }

    public BigDecimal getOrderTotal() {
        try {
            String[] order = StringUtils.split(orderTotal.getText(), " ");
            return new BigDecimal(order[0]);
        } catch (StaleElementReferenceException te) {
            return getOrderTotal();
        }
    }

    public Cart clickIHaveDiscountCode() {
        safeClick(iHaveADiscountCode, driver);
        return this;
    }

    public Cart fillDiscountCode(String code) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(discountCode));
        discountCode.sendKeys(code);
        return this;
    }

    public Cart discountApply() {
        discountApply.click();
        return this;
    }

}
