package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        if (savedRequest != null) {
            if (savedRequest.getParameterMap().containsKey("successSignup"))
                model.addAttribute("successSignup", true);
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupView(@ModelAttribute("user") User user) { //needs to review
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
            return "redirect:login?successSignup=true";
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }
    }

}
