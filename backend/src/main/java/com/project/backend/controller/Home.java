package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.backend.repository.BackendStorage;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    /**
     * Saves a file uploaded by the client to the backend storage.
     *
     * @param file The file to be saved.
     * @return A string representation of the saved file.
     */
    @PostMapping("/file")
    public String postFile(@RequestParam(name = "file", required = true) MultipartFile file) {
        storage.saveBlob(file, Arrays.asList(file.getOriginalFilename()));
        return file.toString();
    }

    /**
     * Deletes a file from the backend storage.
     *
     * @param file The file to be deleted.
     * @return The original filename of the deleted file.
     */
    @DeleteMapping("/file")
    public String deleteFile(@RequestParam(name = "file", required = true) MultipartFile file) {
        storage.deleteBlob(Arrays.asList(file.getOriginalFilename()));
        return file.getOriginalFilename();
    }
}
