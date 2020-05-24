package base;

import base.config.ConfigurationHolder;
import base.pages.Home;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class SeleniumSession implements AutoCloseable {

    WebDriver driver;

    public SeleniumSession() {
        String webDriverType = ConfigurationHolder.configuration().getString("webDriver.type");

        switch (webDriverType) {
            case "Firefox":
                System.setProperty("webdriver.gecko.driver", "src\\test\\java\\base\\drivers\\geckodriver.exe");
                this.driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;
            case "PhantomJs":
                DesiredCapabilities dCaps = new DesiredCapabilities();
                dCaps.setJavascriptEnabled(true);
                dCaps.setCapability("takeScreenshot",false);
                File exeFile = new File("src\\test\\java\\base\\drivers\\phantomjs.exe");
                PhantomJSDriverService pjds = new PhantomJSDriverService.Builder()
                        .usingPhantomJSExecutable(exeFile).usingAnyFreePort().build();
                this.driver = new PhantomJSDriver(pjds,dCaps);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + webDriverType);
        }


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
