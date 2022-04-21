package ru.netology.testmode;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.utils.DataGenerator;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;
import static ru.netology.testmode.utils.DataGenerator.*;

public class TestTestMode {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @AfterEach
    void closeChrome() {
        closeWindow();
    }

    @Test
    void shouldCreateUserAndLoginToAccount() {
        var registrationInfo = createRegisterUserWithStatus(ACTIVE);
        $("[data-test-id=login] input").setValue(registrationInfo.getLogin());
        $("[data-test-id=password] input").setValue(registrationInfo.getPassword());
        $$(byXpath("//*[@data-test-id='action-login']/span/span[1]")).find(exactText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldCreateBlockedUserWithoutLogin() {
        var registrationInfo = createRegisterUserWithStatus(BLOCKED);
        $("[data-test-id=login] input").setValue(registrationInfo.getLogin());
        $("[data-test-id=password] input").setValue(registrationInfo.getPassword());
        $$(byXpath("//*[@data-test-id='action-login']/span/span[1]")).find(exactText("Продолжить")).click();
       $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(5));
        $(withText("Пользователь заблокирован"))
                .shouldBe(visible,Duration.ofSeconds(5));
    }
    @Test
    void shouldNotSendFormWithIncorrectUserData() {
        var registrationInfo = DataGenerator.generateInfo("ru",ACTIVE);
        $("[data-test-id=login] input").setValue(registrationInfo.getLogin());
        $("[data-test-id=password] input").setValue(registrationInfo.getPassword());
        $$(byXpath("//*[@data-test-id='action-login']/span/span[1]")).find(exactText("Продолжить")).click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(5));
        $(withText("Неверно указан логин или пароль"))
                .shouldBe(visible,Duration.ofSeconds(5));
    }

    @Test
    void shouldNotSendFormWithIncorrectLogin() {
        var registrationInfo = createRegisterUserWithStatus(ACTIVE);
        $("[data-test-id=login] input").setValue(generateRandomString());
        $("[data-test-id=password] input").setValue(registrationInfo.getPassword());
        $$(byXpath("//*[@data-test-id='action-login']/span/span[1]")).find(exactText("Продолжить")).click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(5));
        $(withText("Неверно указан логин или пароль"))
                .shouldBe(visible,Duration.ofSeconds(5));
    }

    @Test
    void shouldNotSendFormWithIncorrectPassword() {
        var registrationInfo = createRegisterUserWithStatus(ACTIVE);
        $("[data-test-id=login] input").setValue(registrationInfo.getLogin());
        $("[data-test-id=password] input").setValue(generateRandomString());
        $$(byXpath("//*[@data-test-id='action-login']/span/span[1]")).find(exactText("Продолжить")).click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(5));
        $(withText("Неверно указан логин или пароль"))
                .shouldBe(visible,Duration.ofSeconds(5));
    }








}