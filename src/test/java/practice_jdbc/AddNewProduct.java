package practice_jdbc;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddNewProduct extends ForManyTest{
   @Test
    void addProduct() throws InterruptedException, SQLException {
        //Нажатие на падающий список "Песочница"
        WebElement buttonSandbox = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandbox.click();

        //Нажатие на кнопку "Товары" в падающем списке
        WebElement buttonFood = driver.findElement(By.xpath("//a[@href='/food']"));
        buttonFood.click();

        //Нажатие на кнопку "Добавить"
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        WebElement buttonAdd = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        buttonAdd.click();

        //Ввод текста "Чеснок" в текстовое поле
        WebElement textName = driver.findElement(By.xpath("//input[@id='name']"));
        textName.sendKeys("Чеснок");
        assertEquals("Чеснок", textName.getAttribute("value"), "Должно быть слово *Чеснок*");

        //Выбор значения "Овощ" в выпадающем списке
        WebElement typeProduct = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        typeProduct.click();
        assertEquals("Овощ", typeProduct.getText(), "Должно быть слово *Овощ*");

        //Проверка на нажатие чекбокса
        WebElement checkboxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (checkboxExotic.isSelected()) {
            checkboxExotic.click();
        }
        assertEquals(false, checkboxExotic.isSelected(), "Чекбокс должен быть false");

        //Нажатие на кнопку "Сохранить"
        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSave.click();

        Thread.sleep(1000);

        //Проверка того, что товар добавился в БД
        resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
               "WHERE FOOD_NAME = 'Чеснок' AND FOOD_TYPE = 'VEGETABLE' AND FOOD_EXOTIC ='0.00' " +
                "AND FOOD_ID = (SELECT MAX(FOOD_ID) FROM FOOD)");
        if (resultSet.next()) {
            if(resultSet.getInt("count_name") < 1){
                throw new IllegalStateException("Товар не добавился");
            }
        }

       //Удаление последнего добавленного товара из БД
       PreparedStatement ps = connection.prepareStatement("DELETE FROM FOOD " +
               "WHERE FOOD_ID = (SELECT MAX(FOOD_ID) FROM FOOD) AND FOOD_NAME = 'Чеснок'");
       int rowsAffected = ps.executeUpdate();
       if (rowsAffected > 0) {
           System.out.println("Последняя добавленная строка успешно удалена.");
       } else {
           System.out.println("Таблица пуста или нет строк для удаления.");
       }

       //Проверка, что товара нет в БД
       resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
               "WHERE FOOD_NAME = 'Чеснок' AND FOOD_TYPE = 'VEGETABLE' AND FOOD_EXOTIC ='0.00' " +
               "AND FOOD_ID = (SELECT MAX(FOOD_ID) FROM FOOD)");
       if (resultSet.next()) {
           if(resultSet.getInt("count_name") != 0){
               throw new IllegalStateException("Товар не был удален");
           }
       }

    }

}
