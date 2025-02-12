package com.microapp2.Micro.App.Controller;

import com.microapp2.Micro.App.Entities.Role;
import com.microapp2.Micro.App.Entities.User;
import com.microapp2.Micro.App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/doLogin")
    public String loginUser(@ModelAttribute User user, Model model) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            System.out.println("Logging in user: " + existingUser.getEmail() + ", role: " + existingUser.getRole());
            // Compare passwords directly without encoding
            if (user.getPassword().equals(existingUser.getPassword())) {
                if (existingUser.getRole() == Role.PHYSICAL) {
                    return "redirect:/physical-options";
                } else if (existingUser.getRole() == Role.DIGITAL) {
                    return "redirect:/digital-options";
                }
                return "redirect:/home";
            } else {
                model.addAttribute("error", "Invalid password");
            }
        } else {
            model.addAttribute("error", "User not found");
        }
        return "login";
    }

    @GetMapping("/physical-options")
    public String showPhysicalOptionsPage() {
        return "physical-options";
    }

    @GetMapping("/digital-options")
    public String showDigitalOptionsPage() {
        return "digital-options"; // Ensure digital-options.html exists
    }
}