package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) { //need to review
        model.addAttribute("user");
        return "signup";
    }

    @PostMapping("/signup")
    public String addMessage(@ModelAttribute("user") User user, Model model) {
//        messageListService.addMessage(messageForm.getText());
//        model.addAttribute("greetings", messageListService.getMessages());
//        messageForm.setText("");
        System.out.println(user.getFirstName());
        return null;
    }
}
