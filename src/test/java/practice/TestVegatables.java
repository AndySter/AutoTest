package practice;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVegatables extends BeforeAfterTest {

    @Test
    void addVegatables(){
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

        //Ввод текста "Огурец" в текстовое поле
        WebElement textName = driver.findElement(By.xpath("//input[@id='name']"));
        textName.sendKeys("Огурец");
        assertEquals("Огурец", textName.getAttribute("value"), "Должно быть слово *Огурец*");

        //Выбор значения "Овощ" в выпадающем списке
        WebElement typeProduct = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        typeProduct.click();
        assertEquals("Овощ", typeProduct.getText(), "Должно быть слово *Овощ*");

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
        WebElement addedElementFirst = driver.findElement(By.xpath("//td[text()='Огурец']"));
        assertTrue(addedElementFirst.isDisplayed());
        assertEquals("Огурец", addedElementFirst.getText(), "Добавленный элемент должен называться *Огурец*");

        //Нажатие на кнопку "Добавить"
        WebElement buttonAddSecond = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAddSecond.click();

        //Ввод текста "Бамия" в текстовое поле
        WebElement textNameSecond = driver.findElement(By.xpath("//input[@id='name']"));
        textNameSecond.sendKeys("Бамия");
        assertEquals("Бамия", textNameSecond.getAttribute("value"), "Должно быть слово *Бамия*");

        //Выбор значения "Овощ" в выпадающем списке
        WebElement typeProductSecond = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        typeProductSecond.click();
        assertEquals("Овощ", typeProductSecond.getText(), "Должно быть слово *Овощ*");

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
        WebElement addedElementSecond = driver.findElement(By.xpath("//td[text()='Бамия']"));
        assertTrue(addedElementSecond.isDisplayed());
        assertEquals("Бамия", addedElementSecond.getText(), "Добавленный элемент должен называться *Бамия*");
    }
}
