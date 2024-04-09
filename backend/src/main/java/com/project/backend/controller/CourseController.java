package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Course.Course;
import com.project.backend.Student.Student;
import com.project.backend.repository.FirestoreRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






@RestController
@RequestMapping("/Course")
public class CourseController {
    @Autowired
    private FirestoreRepository repository;

    // get Course by ID
    @GetMapping("/FindCourseByID")
    public Course getCourse(@RequestParam String id) {
        DocumentSnapshot documentSnapshot = repository.getDocumentById(Course.class, id);
        Course course = documentSnapshot.toObject(Course.class);

        return (course != null )? course : null;

    }

    // get Course by price
    @GetMapping("/FindAllCourseByPrice")
    public List<Course> getCoursebyField(@RequestParam Integer value) {
        List<DocumentSnapshot> snapshot = repository.getAllDocumentsByField(Course.class, "price", value);
        Course one = snapshot.get(0).toObject(Course.class);
        List<Course> courses = new ArrayList<Course>();

        courses.add(one);
        return (courses.isEmpty())? null : courses;

    }


    // add student into course
    @PostMapping("/Add/Student")
    public String addStudent(@RequestParam String Name, 
                            @RequestParam Timestamp DayOfBirth, 
                            @RequestParam String Email, 
                            @RequestParam String CourseID )
    {
        
        Student student = new Student(Name, DayOfBirth, Email, null, false);          
        return null;

    }
}
