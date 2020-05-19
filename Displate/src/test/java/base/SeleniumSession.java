package base;

import base.pages.Home;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumSession implements AutoCloseable {

    WebDriver driver;

    public SeleniumSession() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\java\\base\\drivers\\geckodriver.exe");
        this.driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    public void close() {
        this.driver.close();
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public Home homePage() {
        return new Home(driver);
    }

}
