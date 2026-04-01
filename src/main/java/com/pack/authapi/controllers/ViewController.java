package com.pack.authapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/login-page";
    }

    @GetMapping("/login-page")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register-page")
    public String showRegisterPage() {
        return "register";
    }
}
