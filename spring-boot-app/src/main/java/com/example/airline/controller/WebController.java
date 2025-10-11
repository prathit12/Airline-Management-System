package com.example.airline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }

    @GetMapping("/ui/flights")
    public String flights() {
        return "flights";
    }

    @GetMapping("/ui/airports")
    public String airports() {
        return "airports";
    }

    @GetMapping("/ui/customers")
    public String customers() {
        return "customers";
    }

    @GetMapping("/ui/bookings")
    public String bookings() {
        return "bookings";
    }

    @GetMapping("/ui/payments")
    public String payments() {
        return "payments";
    }

    @GetMapping("/ui/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/ui/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/test-api")
    public String testApi() {
        return "forward:/test-api.html";
    }
}