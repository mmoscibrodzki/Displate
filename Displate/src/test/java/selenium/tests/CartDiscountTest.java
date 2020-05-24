package selenium.tests;

import base.SeleniumSession;
import base.TestApi;
import base.TestCaseMeta;
import base.checks.SoftAssert;
import base.pages.Cart;
import base.pages.Home;
import base.pages.ProductPage;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

import static base.pages.ProductPage.finish.GLOSS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartDiscountTest {

    @Test
    @TestCaseMeta(testCaseId = "TC_01",
            testCaseName = "Cart amount calculation",
            descritpion = "Test in which it is determined if item that was added to cart with Gloss finish and custom " +
                    "frame has properly calculated price.")
    public void whenProductAddedToCartAndDiscountAppliedThenCalculateDiscountProperly() {

        TestApi.newScenario("whenProductAddedToCartAndDiscountAppliedThenCalculateDiscountProperly");

        try (SeleniumSession session = new SeleniumSession()) {
            WebDriver driver = session.getDriver();
            Home homePage = session.homePage();

            assertEquals("https://displate.com/", driver.getCurrentUrl());

            ProductPage product = homePage.clickBestsellingList(0);

            String originalPrice = product.getPrice();

            product.selectFinish(GLOSS);

            await().atMost(10, TimeUnit.SECONDS).until(() -> !product.getPrice().equals(originalPrice));
            String priceAfterFinish = product.getPrice();

            product.selectFrame(3);

            await().atMost(20, TimeUnit.SECONDS).until(() -> !product.getPrice().equals(priceAfterFinish));

            String priceAfterFinishAndFrame = product.getPrice();
            String displateId = product.getDisplateId();

            product.clickAddToCartButton();

            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(product.proceedToCartButton));

            Cart cart = product.clickProceedToCartButton();

            assertTrue(cart.displateImageLink.getAttribute("href").contains(displateId));
            assertEquals(priceAfterFinishAndFrame, cart.subtotalPrice.getText());

            BigDecimal originalOrderTotal = cart.getOrderTotal();

            cart.selectCountry("United States");

            await().atMost(10, TimeUnit.SECONDS).until(() -> !cart.getOrderTotal().equals(originalOrderTotal));

            BigDecimal orderTotal = cart.getOrderTotal();

            cart.clickIHaveDiscountCode()
                    .fillDiscountCode("TEAMKRADO")
                    .discountApply();

            await().atMost(10, TimeUnit.SECONDS).until(() -> !cart.getOrderTotal().equals(orderTotal));

            BigDecimal orderTotalAfterDiscount = cart.getOrderTotal();

            assertTrue(orderTotal.compareTo(orderTotalAfterDiscount) > 0);

            BigDecimal division = orderTotalAfterDiscount.divide(orderTotal, 1, RoundingMode.HALF_DOWN);
            assertEquals(0, division.compareTo(new BigDecimal("0.8")));


        }
    }

    @After
    public void after() {
        TestApi.endScenario();
    }
}
