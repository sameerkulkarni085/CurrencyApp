package com.springboot.myApp.controller;

import com.springboot.myApp.model.User;
import com.springboot.myApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}