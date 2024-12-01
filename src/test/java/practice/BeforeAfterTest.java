package practice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.fail;

public class BeforeAfterTest {

    static WebDriver driver;

    @BeforeEach
    void setUp(){
        //Загрузка драйвера для Microsoft Edge
        driver = new EdgeDriver();
        System.setProperty("webdriver.msedgedriver.driver","\\src\\test\\resources\\msedgedriver.exe");

        //Открытие браузера во все окно, с запуском страницы
        driver.manage().window().maximize();
        driver.get("http://localhost:8080");
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @AfterEach
    void clear(){
        //Нажатие на падающий список "Песочница"
        WebElement buttonSandboxAgain = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandboxAgain.click();

        //Нажатие в списке на кнопку "Сброс данных"
        WebElement buttonClear = driver.findElement(By.xpath("//a[@id='reset']"));
        buttonClear.click();

        //Проверка на то, что товары удалились из списка с последующим выходом и браузера
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='Лимон']")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='Инжир']")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='Огурец']")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='Бамия']")));
            driver.quit();
        } catch (Exception e) {
            driver.quit();
            fail("Элементы найдены, хотя должны быть удалены");
        }
    }
}
