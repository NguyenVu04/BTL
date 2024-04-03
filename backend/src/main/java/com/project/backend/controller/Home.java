package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Home {
    @GetMapping("/student")
    public String home() {
        return "Hello World";
    }
    
}
