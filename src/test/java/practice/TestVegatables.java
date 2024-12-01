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
        WebElement buttonSandbox = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandbox.click();

        WebElement buttonFood = driver.findElement(By.xpath("//a[@href='/food']"));
        buttonFood.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement buttonAdd = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAdd.click();

        WebElement textName = driver.findElement(By.xpath("//input[@id='name']"));
        textName.sendKeys("Огурец");
        assertEquals("Огурец", textName.getAttribute("value"), "Должно быть слово *Огурец*");

        WebElement typeProduct = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        typeProduct.click();
        assertEquals("Овощ", typeProduct.getText(), "Должно быть слово *Овощ*");

        WebElement checkboxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if(checkboxExotic.isSelected()){
            checkboxExotic.click();
        }
        assertEquals(false, checkboxExotic.isSelected(),"Чекбокс должен быть false");

        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSave.click();

        WebElement addedElementFirst = driver.findElement(By.xpath("//td[text()='Огурец']"));
        assertTrue(addedElementFirst.isDisplayed());
        assertEquals("Огурец", addedElementFirst.getText(), "Добавленный элемент должен называться *Огурец*");

        WebElement buttonAddSecond = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAddSecond.click();

        WebElement textNameSecond = driver.findElement(By.xpath("//input[@id='name']"));
        textNameSecond.sendKeys("Бамия");
        assertEquals("Бамия", textNameSecond.getAttribute("value"), "Должно быть слово *Бамия*");

        WebElement typeProductSecond = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        typeProductSecond.click();
        assertEquals("Овощ", typeProductSecond.getText(), "Должно быть слово *Овощ*");

        WebElement checkboxExoticSecond = driver.findElement(By.xpath("//input[@id='exotic']"));
        if(!checkboxExoticSecond.isSelected()){
            checkboxExoticSecond.click();
        }
        assertEquals(true, checkboxExoticSecond.isSelected(),"Чекбокс должен быть true");

        WebElement buttonSaveSecond = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSaveSecond.click();

        WebElement addedElementSecond = driver.findElement(By.xpath("//td[text()='Бамия']"));
        assertTrue(addedElementSecond.isDisplayed());
        assertEquals("Бамия", addedElementSecond.getText(), "Добавленный элемент должен называться *Бамия*");
    }
}
