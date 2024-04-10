package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Course.Course;
import com.project.backend.Student.Student;
import com.project.backend.Teacher.Teacher;
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
    @GetMapping("/FindCourse/ByID")
    public Course getCourse(@RequestParam String id) {
        DocumentSnapshot documentSnapshot = repository.getDocumentById(Course.class, id);
        Course course = documentSnapshot.toObject(Course.class);

        return (course != null )? course : null;

    }

    // get Course by price
    @GetMapping("/FindAllCourse/ByPrice")
    public List<Course> getCoursebyField(@RequestParam Integer value) {
        List<DocumentSnapshot> snapshot = repository.getAllDocumentsByField(Course.class, "price", value);
        Course one = snapshot.get(0).toObject(Course.class);
        List<Course> courses = new ArrayList<Course>();

        courses.add(one);
        return (courses.isEmpty())? null : courses;

    }


    // add student into course
    @PostMapping("/Add/Student")
    public String addStudentIntoCourse(
                            @RequestParam String studentID, 
                            @RequestParam String CourseID 
                            )
    {
        // get course information
        DocumentSnapshot findStudent = repository.getDocumentById(Student.class, studentID);
        if (findStudent == null) {
            return "Student not exist";
        }        
        Student student = findStudent.toObject(Student.class);
        for (int i = 0 ; i < student.getCourseID().size() ; i++) {
            if (student.getCourseID().get(i).equals(CourseID)){
                return "Student already in this course";
            }
        }
        // inject courseID into that student
        student.getCourseID().add(CourseID);
        
        // inject that student into the course
        Course temp = repository.getDocumentById(Course.class, CourseID).toObject(Course.class);
        temp.getListStudent().add(student);
        repository.updateDocumentById(student);
        repository.updateDocumentById(temp);
        return "Successfully";

    }

    @PostMapping("/Add/Teacher")
    public String addTeacherIntoCourse(@RequestParam String teacherID,
                                       @RequestParam String CourseID) 
    {
                                      
        DocumentSnapshot findTeacher = repository.getDocumentById(Teacher.class, teacherID);
        if (findTeacher == null) {
            return "Teacher not exist";
        }        
        Teacher teacher = findTeacher.toObject(Teacher.class);
        for (int i = 0 ; i < teacher.getCourseID().size() ; i++) {
            if (teacher.getCourseID().get(i).equals(CourseID)){
                return "Teacher already in this course";
            }
        }
        // inject courseID into that teacher
        teacher.getCourseID().add(CourseID);
        
        // inject that teacher into the course
        Course temp = repository.getDocumentById(Course.class, CourseID).toObject(Course.class);
        temp.getListTeacher().add(teacher);
        repository.updateDocumentById(teacher);
        repository.updateDocumentById(temp);
        return "Successfully";
    }
    
}
