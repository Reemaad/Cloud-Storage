package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupView(@ModelAttribute("user") User user, Model model) { //needs to review
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) {
        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "User is already exist.";
        } else {
            int rowNumber = userService.createUser(user);
            if (rowNumber < 0) {
                signupError = "Something went wrong. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signedUpSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }

}
