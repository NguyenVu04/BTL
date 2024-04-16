package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.Timestamp;
import com.project.backend.Student.Student;
import com.project.backend.exceptionhandler.ExceptionLog;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private ExceptionLog exceptionLog;
    @Autowired
    private FirestoreRepository repository;

    // @GetMapping("/Get")
    // public ResponseEntity<Student> getMethodName(
    //     @RequestParam (required = false, defaultValue = "0000") Integer year,
    //     @RequestParam (required = false, defaultValue =  "01") Integer month,
    //     @RequestParam (required = false, defaultValue = "01") Integer day,
    //     @RequestParam (required = false, defaultValue = "00") Integer hour,
    //     @RequestParam (required = false, defaultValue = "00") Integer minute,
    //     @RequestParam (required = false, defaultValue = "00") Integer second
    // ) {
    //     try{
    //         LocalDateTime now = LocalDateTime.of(year, month, day, hour, minute, second);
    //         Timestamp timestamp = convertTimestamp(year, month, day, hour, minute, second);
    //         return timestamp.toString() + " __________________ " + now.toString();

    //     }catch(Exception e){
    //         exceptionLog.log(e);
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    //     }
    //     // LocalDate date = LocalDate.of(2004, 08, 01);
    // }
    

    @PostMapping("/new/id")
    
    public ResponseEntity<Student> setStudent(@RequestParam String name,
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
        try{

            if (repository.getDocumentById(Student.class, id) != null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            List<String> CourseID = new ArrayList<String>();
            
            Timestamp dob = convertTimestamp(year, month, day, hour, minute, second);
            Student student = new Student(name, dob, email, CourseID, false);
            repository.saveDocument(student, id);
            return ResponseEntity.ok().body(student);
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PutMapping("/adjustion/id")
    public ResponseEntity<Map<String, Object>> UpdateStudentbyField(
        @RequestParam String id,
        @RequestParam String field, 
        @RequestParam Object value
        
        ) {
        //TODO: process PUT request
        try{

            DocumentSnapshot snapshot = repository.getDocumentById(Student.class, id);
            if (snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    
            Map<String, Object> obj = snapshot.getData();
            obj.put(field, value);
    
            repository.saveDocument(Student.class, obj);
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PutMapping("/adjustion/id/day-of-birth")
    public ResponseEntity<Map<String, Object>> UpdateBirthday(@RequestParam String id,
                                @RequestParam (required = false, defaultValue = "0000") Integer year,
                                @RequestParam (required = false, defaultValue =  "01") Integer month,
                                @RequestParam (required = false, defaultValue = "01") Integer day,
                                @RequestParam (required = false, defaultValue = "00") Integer hour,
                                @RequestParam (required = false, defaultValue = "00") Integer minute,
                                @RequestParam (required = false, defaultValue = "00") Integer second
    ) {
        try {
            DocumentSnapshot snapshot = repository.getDocumentById(Student.class, id);
            if (snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    
            Timestamp value = convertTimestamp(year, month, day, hour, minute, second);
            Map<String, Object> changes = snapshot.getData();
            changes.put("dob", value);
            repository.updateDocumentById(Student.class, id, changes);
            return ResponseEntity.ok().body(changes);

        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/del")
    public ResponseEntity<String> DeleteStudent(@RequestParam String id) {
        //TODO: process DELETE request
        
        try{            
            DocumentSnapshot snapshot = repository.getDocumentById(Student.class, id);
            if (snapshot == null)  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            
            repository.deleteDocumentById(Student.class, id);
            return ResponseEntity.ok().body(id);
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/id")
    public ResponseEntity<Student> getStudentbyId(@RequestParam String id) {
        try {
            DocumentSnapshot snapshot = repository.getDocumentById(Student.class, id);
            if (snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Student student = snapshot.toObject(Student.class);
            return ResponseEntity.ok().body(student);
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudent() {
        try {
            List<DocumentSnapshot> snapshots = repository.getAllDocuments(Student.class);
            if (snapshots == null || snapshots.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<Student> students = new ArrayList<Student>();
            for (DocumentSnapshot snapshot : snapshots) {
                Student student = snapshot.toObject(Student.class);
                students.add(student);
            }

            return ResponseEntity.ok().body(students);
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    public Timestamp convertTimestamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second){
        LocalDateTime now = LocalDateTime.of(year, month, day, hour, minute, second);
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Timestamp timestamp = Timestamp.of(date);
        return timestamp;
    }
}
