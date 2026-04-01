package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.service.AccountService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;

    @GetMapping
    public String userPage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        model.addAttribute("username", currentUser.getUsername());
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "security/user";
    }

    @PostMapping("/promote")
    public String promoteToAdmin(@AuthenticationPrincipal UserDetails currentUser) {
        accountService.promoteToAdmin(currentUser.getUsername());
        return "redirect:/user";
    }
}
