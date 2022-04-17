package ru.netology.testmode.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DataGenerator {

    public static RegistrationInfo generateInfo(String locale, boolean isActive) {
        Faker faker = new Faker(new Locale(locale));
        return new RegistrationInfo(faker.name().fullName().trim(),
                faker.letterify("??????"),
                isActive ? "active" : "blocked");
    }
}