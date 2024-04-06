import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


public class BaseTest {

    private WebDriver driver;

    @BeforeAll
    static void setupClass(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setup() throws MalformedURLException {
//       // Запуск тестов через удалённый сервер, например при настройках CI/CD
//         DesiredCapabilities abilities = DesiredCapabilities.chrome();
//       --  Новый класс, рекомендуемый вместо DesiredCapabilities
//        // ChromeOptions abilities = new ChromeOptions();
//         driver = new RemoteWebDriver(new URL("http://172.23.16.1:4444"),abilities);

        driver = new ChromeDriver();
    }
    @Test
    void firstTest(){
        driver.navigate().to("http://saucedemo.com");
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        assertTrue(driver.findElement(By.id("inventory_container")).isDisplayed());
    }

    @AfterEach
    void teardown(){
//        Команда driver.close закрывает окно, но не закрывает драйвер браузера
//        driver.close();

        driver.quit();
    }

}

