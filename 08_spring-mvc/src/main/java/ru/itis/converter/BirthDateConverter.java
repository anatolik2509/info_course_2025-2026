package ru.itis.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;

@Component
public class BirthDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        String[] parts = source.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Incorrect date");
        }
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return LocalDate.of(year, month, day);
        } catch (NumberFormatException | DateTimeException e) {
            throw new IllegalArgumentException("Incorrect date");
        }
    }
}
