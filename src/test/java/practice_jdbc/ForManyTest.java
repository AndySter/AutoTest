package practice_jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.sql.*;
import java.util.concurrent.TimeUnit;


public class ForManyTest {
    static WebDriver driver;
    static Connection connection;
    static ResultSet resultSet;
    static Statement statement;

    @BeforeEach
    void connectDB() throws SQLException {
        //Загрузка драйвера для Microsoft Edge
        driver = new EdgeDriver();
        System.setProperty("webdriver.msedgedriver.driver","\\src\\test\\resources\\msedgedriver.exe");

        //Открытие браузера во все окно, с запуском страницы
        driver.manage().window().maximize();
        driver.get("http://localhost:8080");
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Подключение к БД
        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb","user","pass");
        statement = connection.createStatement();

        //Проверка на начальное количество строк
        resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM FOOD");
        while (resultSet.next()) {
            if(resultSet.getInt("row_count") != 4){
                throw new IllegalStateException("Неверное начальное количество строк");
            }
        }
    }
    @AfterEach
    void clear() throws SQLException {
        //Нажатие на падающий список "Песочница"
        WebElement buttonSandboxAgain = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandboxAgain.click();

        //Нажатие в списке на кнопку "Сброс данных"
        WebElement buttonClear = driver.findElement(By.xpath("//a[@id='reset']"));
        buttonClear.click();

        driver.quit();
        connection.close();
    }
}
