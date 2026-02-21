package ru.itis.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.SignUpForm;

@Controller
@RequestMapping("/validation")
public class ValidationController {

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("user", new SignUpForm());
    }

    @GetMapping
    public String getPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid SignUpForm signUpForm, BindingResult result, ModelMap model) {
        System.out.println(signUpForm.toString());
        return "signUp";
    }
}
