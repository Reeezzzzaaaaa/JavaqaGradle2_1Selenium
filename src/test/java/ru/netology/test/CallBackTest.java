package ru.netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class CallBackTest {

    WebDriver driver;

    void ChromeOptions() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "/drivers/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void FirstOptionTest() {

        driver.get("http://localhost:9999/");
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.get(0).sendKeys("Иванов-Петров Михаил");
        inputs.get(1).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void SecondOptionTest() {

        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Петров Михаил");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__text")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void selenideOptionTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=order-success").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.".trim()));
    }

    @Test
    void nameSpecialCharacterTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил@");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.".trim()));
    }

    @Test
    void nameEmptyTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения".trim()));
    }

    @Test
    void nameEngTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("Petrov Ivan");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.".trim()));
    }

    @Test
    void phoneEmptyTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения".trim()));
    }

    @Test
    void phone10SymbolTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+7999123456");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.".trim()));
    }

    @Test
    void phone12SymbolTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+799912345678");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.".trim()));
    }

    @Test
    void agreementTest() {

        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]"));
        $(By.className("button__text")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__box").shouldHave(empty);
    }
}
