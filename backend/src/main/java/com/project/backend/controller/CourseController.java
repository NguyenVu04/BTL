package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Course.Category;
import com.project.backend.Course.Course;
import com.project.backend.Course.Lesson;
import com.project.backend.Course.Quizz.Quizz;
import com.project.backend.Student.Student;
import com.project.backend.Teacher.Teacher;
import com.project.backend.repository.FirestoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


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

    // create a new Course
    @PostMapping("Add/Course")
    public String createCourse(
                                @RequestParam String name,
                                @RequestParam Double price,
                                @RequestParam String id
                                ) 
    {
            
        if (repository.getDocumentById(Course.class, name) != null) {
            return "Course already exist";
        }
        Category category = new Category(null, null);
        List<Lesson> LessonMaterials = new ArrayList<Lesson>();
		List<Student> students = new ArrayList<Student>();
		List<Teacher> teachers = new ArrayList<Teacher>();
		List<Map<String,Quizz>> quizz = new ArrayList<Map<String,Quizz>>();

        Timestamp now = Timestamp.now();
        Timestamp later = Timestamp.ofTimeMicroseconds((now.getSeconds()+10713600)*1000000);
		Course course = new Course(name, category, now, later, LessonMaterials, price, quizz, null, students, teachers);
        repository.saveDocument(course, id);
        return "Succefully";
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
    

    @PutMapping("/Update/Course")
    public String updateCourse(@RequestParam String field, @RequestParam Object value, @RequestParam String id) {
        DocumentSnapshot documentSnapshot = repository.getDocumentById(Course.class, id);
        if (documentSnapshot == null) {
            return "Course not exist";
        }
        Object find = documentSnapshot.get(field);
        if (find == null) {
            return "Can't find field: " + field;
        }

        Map<String, Object> obj = repository.getDocumentById(Course.class, id).getData();
        obj.put(field, value);

        repository.updateDocumentById(Course.class, id, obj);
        return "Succefully";
    }

    @DeleteMapping("/Delete/Course")
    public String deleteCourse(@RequestParam String id) {
        DocumentSnapshot documentSnapshot = repository.getDocumentById(Course.class, id);
        if (documentSnapshot == null) {
            return "Course not exist";
        }
        repository.deleteDocumentById(Course.class, id);
        return "Succefully";
    }

    @DeleteMapping("/Delete/Student")
    public String deleteStudentinCourse(@RequestParam String id, @RequestParam String idCourse) {
        DocumentSnapshot documentSnapshot = repository.getDocumentById(Student.class, id);
        if (documentSnapshot == null) {
            return "Student not exist";
        }
        DocumentSnapshot snapshot = repository.getDocumentById(Course.class, idCourse);
        if (snapshot == null) {
            return "Course not exist";
        }

        Student student = documentSnapshot.toObject(Student.class);
        String email = student.getEmail();
        boolean alreadyExists = false;
        for (int i = 0 ; i < student.getCourseID().size() ; i++) {
            if (student.getCourseID().get(i).equals(idCourse)){
                student.getCourseID().remove(i);
                alreadyExists = true;
                repository.updateDocumentById(student);
                break;
            }
        }
        if (alreadyExists == false) {
            return "Student doesn't exist in this course";
        }
        Course course = snapshot.toObject(Course.class);
        // course.getListStudent()
        for (int i = 0 ; i <  course.getListStudent().size(); i++) {
            if (course.getListStudent().get(i).getEmail().equals(email)) {
                course.getListStudent().remove(i);
                break;
            }
        }
        repository.updateDocumentById(course);
        return "Succefully";

    }
    @DeleteMapping("/Delete/Teacher")
    public String deleteTeacherinCourse(@RequestParam(defaultValue = "non") String id, @RequestParam(required = false) String idCourse) {
        DocumentSnapshot documentSnapshot = repository.getDocumentById(Teacher.class, id);
        if (documentSnapshot == null) {
            return "Teacher not exist";
        }
        DocumentSnapshot snapshot = repository.getDocumentById(Course.class, idCourse);
        if (snapshot == null) {
            return "Course not exist";
        }

        Teacher teacher = documentSnapshot.toObject(Teacher.class);
        String email = teacher.getEmail();
        boolean alreadyExists = false;
        for (int i = 0 ; i < teacher.getCourseID().size() ; i++) {
            if (teacher.getCourseID().get(i).equals(idCourse)){
                teacher.getCourseID().remove(i);
                repository.updateDocumentById(teacher);
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists == false) {
            return "Teacher doesn't exist in this course";
        }
        Course course = snapshot.toObject(Course.class);
        for (int i = 0 ; i <  course.getListTeacher().size(); i++) {
            if (course.getListTeacher().get(i).getEmail().equals(email)) {
                course.getListTeacher().remove(i);
                break;
            }
        }
        repository.updateDocumentById(course);
        return "Succefully";

    }



    @GetMapping("/Foo")
    public String addFoo(@RequestParam(name = "id", required = false) String fooId, @RequestParam String name) { 
        return "ID: " + fooId + " Name: " + name;
    }
    
}
