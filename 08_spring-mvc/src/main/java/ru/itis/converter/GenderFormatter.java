package ru.itis.converter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.itis.dto.Gender;

import java.text.ParseException;
import java.util.Locale;

@Component
public class GenderFormatter implements Formatter<Gender> {
    @Override
    public Gender parse(String text, Locale locale) throws ParseException {
        if (text.equals("М")) {
            return Gender.MALE;
        }
        if (text.equals("Ж")) {
            return Gender.FEMALE;
        }
        throw new IllegalArgumentException("Invalid gender");
    }

    @Override
    public String print(Gender object, Locale locale) {
        return switch (object) {
            case MALE -> "М";
            case FEMALE -> "Ж";
        };
    }
}
