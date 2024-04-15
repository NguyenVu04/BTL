package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Teacher.Teacher;
import com.project.backend.repository.FirestoreRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/teacher")

public class TeacherController {
    
    @Autowired
    private FirestoreRepository repository;

    @GetMapping("/id")
    public String findTeacher(@RequestParam String id) {
        DocumentSnapshot documentSnapshot = repository.getDocumentById(Teacher.class, id);
        if (documentSnapshot == null) {
            return "Teacher doesn't exist";
        }

        
        return documentSnapshot.toObject(Teacher.class).getName();
    }
    
    @PutMapping("/id/info")
    public String putMethodName(@RequestParam String id,
                                @RequestParam String field,
                                @RequestParam Object value) 
    {
        //TODO: process PUT request
        
        DocumentSnapshot snapshot = repository.getDocumentById(Teacher.class, id);
        if (snapshot == null) {
            return "Student doesn't exist";
        }

        Map<String, Object> obj = snapshot.getData();
        obj.put(field, value);

        repository.saveDocument(Teacher.class, obj);
        return "Successfully";
    }

    @PutMapping("/id/info/day-of-birth")
    public String updateDOB(
        @RequestParam String id, 
        @RequestParam (required = false, defaultValue = "0000") Integer year,
        @RequestParam (required = false, defaultValue =  "01") Integer month,
        @RequestParam (required = false, defaultValue = "01") Integer day,
        @RequestParam (required = false, defaultValue = "00") Integer hour,
        @RequestParam (required = false, defaultValue = "00") Integer minute,
        @RequestParam (required = false, defaultValue = "00") Integer second
        ) {
        //TODO: process PUT request
        
        DocumentSnapshot snapshot = repository.getDocumentById(Teacher.class, id);
        if (snapshot == null) {
            return "Student doesn't exist";
        }

        Timestamp value = convertTimestamp(year, month, day, hour, minute, second);
        Map<String, Object> changes = snapshot.getData();
        changes.put("dob", value);
        repository.updateDocumentById(Teacher.class, id, changes);
        return "Successfully";
    }
    
    @DeleteMapping("/id/del")
    public String DeleteStudent(@RequestParam String id) {
        //TODO: process DELETE request
        
        DocumentSnapshot snapshot = repository.getDocumentById(Teacher.class, id);
        if (snapshot == null) {
            return "Student doesn't exist";
        }
        repository.deleteDocumentById(Teacher.class, id);
        return "Successfully";
    }

    public Timestamp convertTimestamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second){
        LocalDateTime now = LocalDateTime.of(year, month, day, hour, minute, second);
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Timestamp timestamp = Timestamp.of(date);
        return timestamp;
    }
}
