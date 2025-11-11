package com.quizapp.quizapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/testhello")
    public String hello() {
        return "Hello from /testhello";
    }
}
