package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.Timestamp;
import com.project.backend.Student.Student;
import com.project.backend.repository.FirestoreRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/Student")
public class StudentController {

    @Autowired
    private FirestoreRepository repository;

    @GetMapping("/Get")
    public String getMethodName(
        @RequestParam (required = false, defaultValue = "0000") Integer year,
        @RequestParam (required = false, defaultValue =  "01") Integer month,
        @RequestParam (required = false, defaultValue = "01") Integer day,
        @RequestParam (required = false, defaultValue = "00") Integer hour,
        @RequestParam (required = false, defaultValue = "00") Integer minute,
        @RequestParam (required = false, defaultValue = "00") Integer second
    ) {
        // LocalDate date = LocalDate.of(2004, 08, 01);
        LocalDateTime now = LocalDateTime.of(year, month, day, hour, minute, second);
        Timestamp timestamp = convertTimestamp(year, month, day, hour, minute, second);
        return timestamp.toString() + " __________________ " + now.toString();
    }
    

    @PostMapping("/Add")
    
    public String setStudent(@RequestParam String name,
                            @RequestParam(required = false) String email,
                            @RequestParam String id,
                            @RequestParam (required = false, defaultValue = "0000") Integer year,
                            @RequestParam (required = false, defaultValue =  "01") Integer month,
                            @RequestParam (required = false, defaultValue = "01") Integer day,
                            @RequestParam (required = false, defaultValue = "00") Integer hour,
                            @RequestParam (required = false, defaultValue = "00") Integer minute,
                            @RequestParam (required = false, defaultValue = "00") Integer second
                            )
    {   

        if (repository.getDocumentById(Student.class, id) != null){
            return "Student already exists";
        }
        List<String> CourseID = new ArrayList<String>();
        
        Timestamp dob = convertTimestamp(year, month, day, hour, minute, second);
        Student student = new Student(name, dob, email, CourseID, false);
        repository.saveDocument(student, id);
        return "Successfully";
    }

    @PutMapping("Update")
    public String UpdateStudentbyField(
        @RequestParam String id,
        @RequestParam String field, 
        @RequestParam Object value
        
        ) {
        //TODO: process PUT request
        
        DocumentSnapshot snapshot = repository.getDocumentById(Student.class, id);
        if (snapshot == null) {
            return "Student doesn't exist";
        }

        Map<String, Object> obj = snapshot.getData();
        obj.put(field, value);

        repository.saveDocument(Student.class, obj);
        return "Successfully";
    }


    @PutMapping("Update/DOB")
    public String UpdateBirthday(@RequestParam String id,
                                @RequestParam (required = false, defaultValue = "0000") Integer year,
                                @RequestParam (required = false, defaultValue =  "01") Integer month,
                                @RequestParam (required = false, defaultValue = "01") Integer day,
                                @RequestParam (required = false, defaultValue = "00") Integer hour,
                                @RequestParam (required = false, defaultValue = "00") Integer minute,
                                @RequestParam (required = false, defaultValue = "00") Integer second
    ) {
        //TODO: process PUT request
        
        DocumentSnapshot snapshot = repository.getDocumentById(Student.class, id);
        if (snapshot == null) {
            return "Student doesn't exist";
        }

        Timestamp value = convertTimestamp(year, month, day, hour, minute, second);
        Map<String, Object> changes = snapshot.getData();
        changes.put("dob", value);
        repository.updateDocumentById(Student.class, id, changes);
        return "Successfully";
    }

    @DeleteMapping("/Delete")
    public String DeleteStudent(@RequestParam String id) {
        //TODO: process DELETE request
        
        DocumentSnapshot snapshot = repository.getDocumentById(Student.class, id);
        if (snapshot == null) {
            return "Student doesn't exist";
        }
        repository.deleteDocumentById(Student.class, id);
        return "Successfully";
    }

    public Timestamp convertTimestamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second){
        LocalDateTime now = LocalDateTime.of(year, month, day, hour, minute, second);
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Timestamp timestamp = Timestamp.of(date);
        return timestamp;
    }
}
