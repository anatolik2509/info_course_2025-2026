package ru.itis.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.RegisterForm;
import ru.itis.model.Account;
import ru.itis.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities(account.getRole())
                .build();
    }

    public boolean register(RegisterForm form) {
        if (accountRepository.existsByUsername(form.getUsername())) {
            return false;
        }
        accountRepository.save(Account.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .role("ROLE_USER")
                .build());
        return true;
    }
}
