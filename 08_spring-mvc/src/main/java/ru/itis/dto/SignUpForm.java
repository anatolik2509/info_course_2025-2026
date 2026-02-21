package ru.itis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.itis.validator.EqualsPasswords;

@Data
@EqualsPasswords(message = "password incorrect", password = "password", passwordRepeat = "passwordRepeat")
public class SignUpForm {
    @Size(max = 20, message = "too long nick (20 characters max)")
    private String login;
    @Email(message = "incorrect email")
    private String email;
    private String password;
    private String passwordRepeat;
}
