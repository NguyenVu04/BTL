package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Home {
    @Autowired
    @GetMapping("/")
    public String home() {
        return "Hello World";
    }
    
}
