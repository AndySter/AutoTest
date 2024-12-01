package practice_jdbc;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddExistingProduct extends ForManyTest {

    @Test
    void addExisting() throws SQLException, InterruptedException {
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
        textName.sendKeys("Апельсин");
        assertEquals("Апельсин", textName.getAttribute("value"), "Должно быть слово *Апельсин*");

        //Выбор значения "Фрукт" в выпадающем списке
        WebElement typeProduct = driver.findElement(By.xpath("//option[@value='FRUIT']"));
        typeProduct.click();
        assertEquals("Фрукт", typeProduct.getText(), "Должно быть слово *Фрукт*");

        //Проверка на нажатие чекбокса
        WebElement checkboxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (!checkboxExotic.isSelected()) {
            checkboxExotic.click();
        }
        assertEquals(true, checkboxExotic.isSelected(), "Чекбокс должен быть true");

        //Нажатие на кнопку "Сохранить"
        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSave.click();

        Thread.sleep(1000);

        //Проверка того, что добавился одинаковый товар в БД
        resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
                "WHERE FOOD_NAME = 'Апельсин' AND FOOD_TYPE = 'FRUIT' AND FOOD_EXOTIC ='1.00'");
        if (resultSet.next()) {
            if(resultSet.getInt("count_name") != 2){
                throw new IllegalStateException("Неверное количество одинаковых товаров");
            }
        }

        //Удаление последнего добавленного товара из БД
        PreparedStatement ps = connection.prepareStatement("DELETE FROM FOOD WHERE FOOD_ID = (SELECT MAX(FOOD_ID) FROM FOOD) AND FOOD_NAME = 'Апельсин'");
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Последняя добавленная строка успешно удалена.");
        } else {
            System.out.println("Таблица пуста или нет строк для удаления.");
        }

        //Проверка, что похожих товаров нет
        resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
                "WHERE FOOD_NAME = 'Апельсин' AND FOOD_TYPE = 'FRUIT' AND FOOD_EXOTIC ='1.00'");
        if (resultSet.next()) {
            if(resultSet.getInt("count_name") != 1){
                throw new IllegalStateException("Неверное количество одинаковых товаров");
            }
        }

        //Проверка того, что товар удалился из БД
        resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM FOOD");
        if (resultSet.next()) {
            if(resultSet.getInt("row_count") != 4){
                throw new IllegalStateException("Неверное количество строк");
            }
        }
    }
}
