package ru.netology.testmode.utils;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;


@UtilityClass
public class DataGenerator {

    private static final Gson GSON = new Gson();
    private static final Faker FAKER = new Faker(new Locale("ru"));
    public static final String STRING_MASK = "??????";

    public static RegistrationInfo generateInfo(String status) {
        return new RegistrationInfo(FAKER.name().fullName().trim(),
                FAKER.letterify(STRING_MASK),
                status);
    }

    public static String generateRandomString(){
        return FAKER.bothify(STRING_MASK);
    }

    @Value
    public static class RegistrationInfo {
        private final String login;
        private final String password;
        private final String status;
    }

    public static DataGenerator.RegistrationInfo createRegisterUserWithStatus(String status) {
        var registrationInfo = DataGenerator.generateInfo(status);
        String jsonUserInfo = GSON.toJson(registrationInfo);
        given()
                .baseUri("http://localhost:9999")
                .body(jsonUserInfo)
                .when()
                .contentType("application/json")
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return registrationInfo;
    }
}