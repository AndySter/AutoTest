package practice;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TestFruits extends BeforeAfterTest {

    @Test
    void addFruit(){
        //Нажатие на падающий список "Песочница"
        WebElement buttonSandbox = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandbox.click();

        //Нажатие на кнопку "Товары" в падающем списке
        WebElement buttonFood = driver.findElement(By.xpath("//a[@href='/food']"));
        buttonFood.click();

        //Нажатие на кнопку "Добавить"
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement buttonAdd = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAdd.click();

        //Ввод текста "Лимон" в текстовое поле
        WebElement textName = driver.findElement(By.xpath("//input[@id='name']"));
        textName.sendKeys("Лимон");
        assertEquals("Лимон", textName.getAttribute("value"), "Должно быть слово *Лимон*");

        //Выбор значения "Фрукт" в выпадающем списке
        WebElement typeProduct = driver.findElement(By.xpath("//option[@value='FRUIT']"));
        typeProduct.click();
        assertEquals("Фрукт", typeProduct.getText(), "Должно быть слово *Фрукт*");

        //Проверка на нажатие чекбокса
        WebElement checkboxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if(checkboxExotic.isSelected()){
        checkboxExotic.click();
        }
        assertEquals(false, checkboxExotic.isSelected(),"Чекбокс должен быть false");

        //Нажатие на кнопку "Сохранить"
        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSave.click();

        //Проверка, что товар добавился в список
        WebElement addedElementFirst = driver.findElement(By.xpath("//td[text()='Лимон']"));
        assertTrue(addedElementFirst.isDisplayed());
        assertEquals("Лимон", addedElementFirst.getText(), "Добавленный элемент должен называться *Лимон*");

        //Нажатие на кнопку "Добавить"
        WebElement buttonAddSecond = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAddSecond.click();

        //Ввод текста "Инжир" в текстовое поле
        WebElement textNameSecond = driver.findElement(By.xpath("//input[@id='name']"));
        textNameSecond.sendKeys("Инжир");
        assertEquals("Инжир", textNameSecond.getAttribute("value"), "Должно быть слово *Инжир*");

        //Выбор значения "Фрукт" в выпадающем списке
        WebElement typeProductSecond = driver.findElement(By.xpath("//option[@value='FRUIT']"));
        typeProductSecond.click();
        assertEquals("Фрукт", typeProductSecond.getText(), "Должно быть слово *Фрукт*");

        //Проверка на нажатие чекбокса
        WebElement checkboxExoticSecond = driver.findElement(By.xpath("//input[@id='exotic']"));
        if(!checkboxExoticSecond.isSelected()){
            checkboxExoticSecond.click();
        }
        assertEquals(true, checkboxExoticSecond.isSelected(),"Чекбокс должен быть true");

        //Нажатие на кнопку "Сохранить"
        WebElement buttonSaveSecond = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSaveSecond.click();

        //Проверка, что товар добавился в список
        WebElement addedElementSecond = driver.findElement(By.xpath("//td[text()='Инжир']"));
        assertTrue(addedElementSecond.isDisplayed());
        assertEquals("Инжир", addedElementSecond.getText(), "Добавленный элемент должен называться *Инжир*");
    }
}
