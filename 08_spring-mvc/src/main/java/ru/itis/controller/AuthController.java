package ru.itis.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.RegisterForm;
import ru.itis.service.AccountService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "security/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisterForm form,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "security/register";
        }

        boolean registered = accountService.register(form);
        if (!registered) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "security/register";
        }

        // После регистрации — на страницу входа с сообщением об успехе
        return "redirect:/login?registered=true";
    }
}
