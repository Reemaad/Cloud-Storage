package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultController {
    @GetMapping("/result")
    public String resultView() {

        return "result";
    }
}
