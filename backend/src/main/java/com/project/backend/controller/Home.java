package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.backend.repository.BackendStorage;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Home {
    @Autowired
    private BackendStorage storage;
    @GetMapping("/home")
    public String home() {
        return "Hello";
    }
    @PostMapping("/file")
    public String postMethodName(@RequestParam(name = "file", required = true) MultipartFile file) {
        storage.updateBlob(file, Arrays.asList());
        return file.toString();
    }
    
}
