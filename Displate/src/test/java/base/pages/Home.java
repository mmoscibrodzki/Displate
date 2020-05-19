package base.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Home extends Page {

    WebDriver driver;

    public static final String PATH = "https://displate.com/";

    public Home(WebDriver driver) {
        this.driver = driver;
        driver.get(PATH);
        PageFactory.initElements(driver, this);
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @FindBy(xpath = "//*[text()='bestselling']/../div/div/div/div/div/div")
    public List<WebElement> bestsellingList;

    @FindBy(xpath = "//button[@onclick='javascript:acceptCookieAlert()']")
    WebElement cookieAlertOkButton;

    public ProductPage clickBestsellingList(int itemNumber) {
        bestsellingList.get(itemNumber).click();
        return new ProductPage(driver);
    }


    private Boolean determineCookieAllertExist() {
        return !driver.findElements(By.xpath("//button[@onclick='javascript:acceptCookieAlert()']")).isEmpty();
    }

}
