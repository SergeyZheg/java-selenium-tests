import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddToBagTest {

    static WebDriver driver;

    @BeforeAll
    static void globalSetup() {
        driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com");
    }

    @BeforeEach
    void setup() {
        Cookie authCookie = new Cookie.Builder("session-username", "standard_user")
                .domain("www.saucedemo.com")
                .path("/")
                .expiresOn(new Date(2024, Calendar.DECEMBER, 31))
                .build();
        driver.manage().addCookie(authCookie);
    }

    @Test
    void addToBagTest() {
        driver.navigate().to("https://www.saucedemo.com/inventory.html");

        String goodText = driver.findElement(By.id("item_4_title_link"))
                .findElement(By.tagName("div"))
                .getText();

        String postfix = goodText.replaceAll(" ", "-").toLowerCase();
        String addToBagButtonId = String.format("add-to-cart-%s", postfix);

        int goodsBefore = getGoodsCount();
        driver.findElement(By.id(addToBagButtonId)).click();
        int goodsAfter = getGoodsCount();

        assertEquals(goodsBefore + 1, goodsAfter, "Должен быть добавлен 1 товар");

        getBagButton().click();

        assertEquals(goodText, driver.findElement(By.id("item_4_title_link")).getText(),
                "В корзине ожидался товар с именем " + goodText);
    }

    @AfterEach
    void clearBrowser() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }

    private int getGoodsCount() {
        String goodsCountStr = getBagButton().getText();
        return goodsCountStr.isBlank() ? 0 : Integer.parseInt(goodsCountStr);
    }

    private WebElement getBagButton() {
        return driver.findElement(By.id("shopping_cart_container"));
    }
}
