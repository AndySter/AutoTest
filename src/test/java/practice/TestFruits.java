package practice;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TestFruits {
    private static WebDriver driver;

    @BeforeEach
    void setUp(){
        driver = new EdgeDriver();
        System.setProperty("webdriver.msedgedriver.driver","\\src\\test\\resources\\msedgedriver.exe");
        driver.manage().window().maximize();

    }

    @AfterEach
    void clear(){

        WebElement buttonSandboxAgain = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandboxAgain.click();

        WebElement buttonClear = driver.findElement(By.xpath("//a[@id='reset']"));
        buttonClear.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='Лимон']")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='Инжир']")));
            System.out.println("Тест проверки добавления фруктов выполнен успешно");
            driver.quit();
        } catch (Exception e) {
            driver.quit();
            fail("Элементы найдены, хотя должны быть удалены");
        }
    }

    @Test
    void addFruit(){
        driver.get("https://qualit.appline.ru/");
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement buttonSandbox = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandbox.click();

        WebElement buttonFood = driver.findElement(By.xpath("//a[@href='/food']"));
        buttonFood.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement buttonAdd = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAdd.click();

        WebElement textName = driver.findElement(By.xpath("//input[@id='name']"));
        textName.sendKeys("Лимон");
        assertEquals("Лимон", textName.getAttribute("value"), "Должно быть слово *Лимон*");

        WebElement typeProduct = driver.findElement(By.xpath("//option[@value='FRUIT']"));
        typeProduct.click();
        assertEquals("Фрукт", typeProduct.getText(), "Должно быть слово *Фрукт*");

        WebElement checkboxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if(checkboxExotic.isSelected()){
        checkboxExotic.click();
        }
        assertEquals(false, checkboxExotic.isSelected(),"Чекбокс должен быть false");

        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSave.click();

        WebElement addedElementFirst = driver.findElement(By.xpath("//td[text()='Лимон']"));
        assertTrue(addedElementFirst.isDisplayed());
        assertEquals("Лимон", addedElementFirst.getText(), "Добавленный элемент должен называться *Лимон*");

        WebElement buttonAddSecond = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAddSecond.click();

        WebElement textNameSecond = driver.findElement(By.xpath("//input[@id='name']"));
        textNameSecond.sendKeys("Инжир");
        assertEquals("Инжир", textNameSecond.getAttribute("value"), "Должно быть слово *Инжир*");

        WebElement typeProductSecond = driver.findElement(By.xpath("//option[@value='FRUIT']"));
        typeProductSecond.click();
        assertEquals("Фрукт", typeProductSecond.getText(), "Должно быть слово *Фрукт*");

        WebElement checkboxExoticSecond = driver.findElement(By.xpath("//input[@id='exotic']"));
        if(!checkboxExoticSecond.isSelected()){
            checkboxExoticSecond.click();
        }
        assertEquals(true, checkboxExoticSecond.isSelected(),"Чекбокс должен быть true");

        WebElement buttonSaveSecond = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSaveSecond.click();

        WebElement addedElementSecond = driver.findElement(By.xpath("//td[text()='Инжир']"));
        assertTrue(addedElementSecond.isDisplayed());
        assertEquals("Инжир", addedElementSecond.getText(), "Добавленный элемент должен называться *Инжир*");
    }
}
