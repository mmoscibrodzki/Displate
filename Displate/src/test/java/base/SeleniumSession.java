package base;

import base.config.ConfigurationHolder;
import base.pages.Home;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                if (ConfigurationHolder.configuration().getBoolean("webDriver.headless")) {
                    firefoxBinary.addCommandLineOptions("--headless");
                }
                System.setProperty("webdriver.gecko.driver", "src\\test\\java\\base\\drivers\\geckodriver.exe");
                FirefoxOptions fo = new FirefoxOptions();
                fo.setBinary(firefoxBinary);
                this.driver = new FirefoxDriver(fo);
                driver.manage().window().setSize(new Dimension(1920,1080));
                break;
            case "PhantomJs":
                DesiredCapabilities dCaps = new DesiredCapabilities();
                dCaps.setJavascriptEnabled(true);
                dCaps.setCapability("takeScreenshot", false);
                File exeFile = new File("src\\test\\java\\base\\drivers\\phantomjs.exe");
                PhantomJSDriverService pjds = new PhantomJSDriverService.Builder()
                        .usingPhantomJSExecutable(exeFile).usingAnyFreePort().build();
                this.driver = new PhantomJSDriver(pjds, dCaps);
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
