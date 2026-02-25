package ru.itis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.itis.validator.EqualsPasswords;

import java.time.LocalDate;

@Data
@EqualsPasswords(message = "Passwords are not equal", password = "password", passwordRepeat = "passwordRepeat")
public class SignUpForm {
    @Size(max = 20, message = "Too long nick (20 characters max)")
    @NotBlank(message = "Empty login")
    private String login;
    @Email(message = "Incorrect email")
    @NotBlank(message = "Empty email")
    private String email;
    @NotBlank(message = "Empty password")
    private String password;
    @NotBlank(message = "Empty password repeat")
    private String passwordRepeat;
    private LocalDate birthDate;
}
