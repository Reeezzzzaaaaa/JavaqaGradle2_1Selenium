package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CallBackTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void selenideOptionTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=order-success").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.".trim()));
    }

    @Test
    void nameSpecialCharacterTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил@");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.".trim()));
    }

    @Test
    void nameEmptyTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения".trim()));
    }

    @Test
    void nameEngTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("Petrov Ivan");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.".trim()));
    }

    @Test
    void phoneEmptyTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения".trim()));
    }

    @Test
    void phone10SymbolTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+7999123456");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.".trim()));
    }

    @Test
    void phone12SymbolTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+799912345678");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.className("button__text")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.".trim()));
    }

    @Test
    void agreementTest() {

        $(By.cssSelector("[data-test-id=name] input")).setValue("Иванов-Петров Михаил");
        $(By.cssSelector("[data-test-id=phone] input")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=agreement]"));
        $(By.className("button__text")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__box").shouldBe(visible);
    }
}
