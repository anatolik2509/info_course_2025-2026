package org.example.controller;

import org.example.model.GithubRepo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class RepoController {

    private final OAuth2AuthorizedClientService clientService;
    private final RestTemplate restTemplate = new RestTemplate();

    public RepoController(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/repos";
        }
        return "index";
    }

    @GetMapping("/repos")
    public String repos(OAuth2AuthenticationToken authentication, Model model) {
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(client.getAccessToken().getTokenValue());
        headers.set("Accept", "application/vnd.github.v3+json");

        ResponseEntity<List<GithubRepo>> response = restTemplate.exchange(
                "https://api.github.com/user/repos?sort=updated&per_page=30",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<GithubRepo>>() {}
        );

        model.addAttribute("repos", response.getBody());
        model.addAttribute("username", authentication.getName());
        return "repos";
    }
}