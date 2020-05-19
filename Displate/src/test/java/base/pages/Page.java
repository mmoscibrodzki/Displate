package base.pages;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public abstract class Page {

    public abstract WebDriver getDriver();

    protected void safeClick(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Long currentScreenPosition = (Long) executor.executeScript("return window.pageYOffset;");
        Long innerHeight = (Long) executor.executeScript("return window.innerHeight;");
        long elementPosition = (long) element.getLocation().y;
        if (elementPosition - currentScreenPosition < 300) {
            executor.executeScript("window.scrollBy(0,-300)");
        }
        if (currentScreenPosition + innerHeight - elementPosition < 300 || currentScreenPosition + innerHeight < elementPosition) {
            executor.executeScript("window.scrollBy(0,300)");
        }
        try {
            element.click();

        } catch (ElementClickInterceptedException t) {
            executor.executeScript("window.scrollBy(0,-400)");
        }
    }
}
