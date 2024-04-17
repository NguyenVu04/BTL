package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Course.Category;
import com.project.backend.Course.Course;
import com.project.backend.Course.Lesson;
import com.project.backend.QuizMain.Quizz;
import com.project.backend.Student.Student;
import com.project.backend.Teacher.Teacher;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.repository.FirestoreRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private FirestoreRepository repository;

    @Autowired
    private ExceptionLog exceptionLog;
    // get all Courses 
    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        try{
            List<DocumentSnapshot> snapshot = repository.getAllDocuments(Course.class);
            if (snapshot.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            List<Course> courses = new ArrayList<Course>();
            for (DocumentSnapshot course : snapshot) {
                courses.add(course.toObject(Course.class));
            }
            return ResponseEntity.ok().body(courses);
        } catch (Exception e){
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    // get Course by ID
    @GetMapping("/id")
    public ResponseEntity<Course> get(@RequestParam String id) {
        try{
            DocumentSnapshot documentSnapshot = repository.getDocumentById(Course.class, id);
            
            if (documentSnapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Course course = documentSnapshot.toObject(Course.class);
            return ResponseEntity.ok().body(course);
            
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    // get Course by price
    @GetMapping("/id/by-price")
    public ResponseEntity<List<Course>> getCoursebyField(@RequestParam Integer value) {
        try{
            List<DocumentSnapshot> snapshot = repository.getAllDocumentsByField(Course.class, "price", value);
            if (snapshot.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            List<Course> courses = new ArrayList<Course>();
            for (DocumentSnapshot course : snapshot) {
                courses.add(course.toObject(Course.class));
            }
            return ResponseEntity.ok().body(courses);
        } catch (Exception e){
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // create a new Course
    @PostMapping("add/course")
    public ResponseEntity<Course> createCourse(
                                @RequestParam String name,
                                @RequestParam Double price,
                                @RequestParam String id
                                ) 
    {
        try{
            if (repository.getDocumentById(Course.class, name) != null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Category category = new Category(null, null);
            List<Lesson> LessonMaterials = new ArrayList<Lesson>();
            List<String> students = new ArrayList<String>();
            List<String> teachers = new ArrayList<String>();
            
            Timestamp now = Timestamp.now();
            Timestamp later = Timestamp.ofTimeMicroseconds((now.getSeconds()+10713600)*1000000);
            List<Quizz> listQuizz = new ArrayList<Quizz>();
            Course course = new Course(name, category, now, later, LessonMaterials, price, null, students, teachers, listQuizz);
            repository.saveDocument(course, id);
            return ResponseEntity.ok(course);

        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    // add student into course
    @PostMapping("/add/student")
    public ResponseEntity<Student> addStudentIntoCourse(
                            @RequestParam String studentID, 
                            @RequestParam String CourseID 
                            )
    {
        try{
            // get course information
            DocumentSnapshot findCourse = repository.getDocumentById(Course.class, CourseID);
            DocumentSnapshot findStudent = repository.getDocumentById(Student.class, studentID);
            
            if (findStudent == null || findCourse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }        
            Student student = findStudent.toObject(Student.class);
            for (int i = 0 ; i < student.getCourseID().size() ; i++) {
                if (student.getCourseID().get(i).equals(CourseID)){
                    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
                }
            }
            // inject courseID into that student
            
            student.getCourseID().add(CourseID);
            
            // inject that student into the course
            Course temp = repository.getDocumentById(Course.class, CourseID).toObject(Course.class);
            temp.getListStudent().add(student.getId());
            repository.updateDocumentById(student);
            repository.updateDocumentById(temp);
            return ResponseEntity.ok().body(student);

        }catch(Exception e){
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/add/teacher")
    public ResponseEntity<Teacher> addTeacherIntoCourse(@RequestParam String teacherID,
                                       @RequestParam String CourseID) 
    {
                       
        try{
            // get course information
            DocumentSnapshot findTeacher = repository.getDocumentById(Teacher.class, teacherID);
            DocumentSnapshot findCourse = repository.getDocumentById(Course.class, CourseID);
            if (findTeacher == null || findCourse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }        
            Teacher teacher = findTeacher.toObject(Teacher.class);
            for (int i = 0 ; i < teacher.getCourseID().size() ; i++) {
                if (teacher.getCourseID().get(i).equals(CourseID)){
                    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
                }
            }
            // inject courseID into that teacher
            teacher.getCourseID().add(CourseID);
            
            // inject that teacher into the course
            Course temp = repository.getDocumentById(Course.class, CourseID).toObject(Course.class);
            temp.getListTeacher().add(teacher.getId());
            repository.updateDocumentById(teacher);
            repository.updateDocumentById(temp);
            return ResponseEntity.ok().body(teacher);
        }catch(Exception e){
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    

    @PutMapping("/adjustion/course")
    public ResponseEntity<Map<String,Object>> updateCourse(@RequestParam String field, @RequestParam Object value, @RequestParam String id) {
        try {
            DocumentSnapshot documentSnapshot = repository.getDocumentById(Course.class, id);
            if (documentSnapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Object find = documentSnapshot.get(field);
            if (find == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    
            Map<String, Object> obj = repository.getDocumentById(Course.class, id).getData();
            obj.put(field, value);
    
            repository.updateDocumentById(Course.class, id, obj);
            return ResponseEntity.ok().body(obj);

        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/del/course")
    public ResponseEntity<Object> deleteCourse(@RequestParam String id) {
        try{
            DocumentSnapshot documentSnapshot = repository.getDocumentById(Course.class, id);
            if (documentSnapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            repository.deleteDocumentById(Course.class, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        } catch (Exception e){
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }   
    }

    @DeleteMapping("/del/student")
    public ResponseEntity<Student> deleteStudentinCourse(@RequestParam String id, @RequestParam String idCourse) {
        try {
            DocumentSnapshot documentSnapshot = repository.getDocumentById(Student.class, id);
            DocumentSnapshot snapshot = repository.getDocumentById(Course.class, idCourse);
            if (documentSnapshot == null || snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    
            Student student = documentSnapshot.toObject(Student.class);
            String idstudent = student.getId();
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
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Course course = snapshot.toObject(Course.class);
            // course.getListStudent()
            for (int i = 0 ; i <  course.getListStudent().size(); i++) {
                if (course.getListStudent().get(i).equals(idstudent)) {
                    course.getListStudent().remove(i);
                    break;
                }
            }
            repository.updateDocumentById(course);
            return ResponseEntity.ok().body(student);

        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
    @DeleteMapping("/del/teacher")
    public ResponseEntity<Teacher> deleteTeacherinCourse(@RequestParam(defaultValue = "non") String id, @RequestParam(required = false) String idCourse) {
        try {
            DocumentSnapshot documentSnapshot = repository.getDocumentById(Teacher.class, id);
            DocumentSnapshot snapshot = repository.getDocumentById(Course.class, idCourse);
            if (documentSnapshot == null || snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        
            Teacher teacher = documentSnapshot.toObject(Teacher.class);
            String teacherid = teacher.getId();
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Course course = snapshot.toObject(Course.class);
            for (int i = 0 ; i <  course.getListTeacher().size(); i++) {
                if (course.getListTeacher().get(i).equals(teacherid)) {
                    course.getListTeacher().remove(i);
                    break;
                }
            }
            repository.updateDocumentById(course);
            return ResponseEntity.ok().body(teacher);

        }
        catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }



    @GetMapping("/Foo")
    public String addFoo(@RequestParam(name = "id", required = false) String fooId, @RequestParam String name) { 
        return "ID: " + fooId + " Name: " + name;
    }
    
    
}
