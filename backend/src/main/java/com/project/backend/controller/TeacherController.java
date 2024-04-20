package com.project.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.Teacher.Certificate;
import com.project.backend.Teacher.Teacher;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;





@RestController
@RequestMapping("/teacher")

public class TeacherController {
    
    @Autowired
    private FirestoreRepository repository;

    @Autowired
    private ExceptionLog exceptionLog;
    
    @Autowired
    private CourseController courseController;
    @GetMapping("/id")
    public ResponseEntity<Teacher> findTeacher(@RequestParam String id) {
        try {
            DocumentSnapshot documentSnapshot = repository.getDocumentById(Teacher.class, id);
            if (documentSnapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    
            Teacher teacher = documentSnapshot.toObject(Teacher.class);
            return ResponseEntity.ok().body(teacher);

        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PutMapping("/id/info")
    public ResponseEntity<Map<String,Object>> putMethodName(@RequestParam String id,
                                @RequestParam String field,
                                @RequestParam Object value) 
    {
        //TODO: process PUT request
        try{
            DocumentSnapshot snapshot = repository.getDocumentById(Teacher.class, id);
            if (snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Map<String, Object> obj = snapshot.getData();
            obj.put(field, value);
    
            repository.saveDocument(Teacher.class, obj);
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/id/info/day-of-birth")
    public ResponseEntity<Map<String,Object>> updateDOB(
        @RequestParam String id, 
        @RequestParam (required = false, defaultValue = "0000") Integer year,
        @RequestParam (required = false, defaultValue =  "01") Integer month,
        @RequestParam (required = false, defaultValue = "01") Integer day,
        @RequestParam (required = false, defaultValue = "00") Integer hour,
        @RequestParam (required = false, defaultValue = "00") Integer minute,
        @RequestParam (required = false, defaultValue = "00") Integer second
        ) {
        //TODO: process PUT request
        try{
            DocumentSnapshot snapshot = repository.getDocumentById(Teacher.class, id);
            if (snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Map<String, Object> obj = snapshot.getData();
            Timestamp value = convertTimestamp(year, month, day, hour, minute, second);
            obj.put("dob", value);
            repository.updateDocumentById(Teacher.class, id, obj);

            return ResponseEntity.ok().body(obj);
        }
        catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @DeleteMapping("/id/del")
    public ResponseEntity<Teacher> DeleteTeacher(@RequestParam String id) {
        //TODO: process DELETE request
        try{
            DocumentSnapshot snapshot = repository.getDocumentById(Teacher.class, id);
            if (snapshot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Teacher teacher = snapshot.toObject(Teacher.class);
            for (int i = 0; i < teacher.getCourseID().size(); i++){
                String getid = teacher.getCourseID().get(i);
                courseController.deleteTeacherinCourse(id, getid);
            }
            repository.deleteDocumentById(Teacher.class, id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/del-all")
    public ResponseEntity<List<Teacher>> deleteAll() {
        try{
            List<Teacher> list = new ArrayList<Teacher>();
            List<DocumentSnapshot> documents = repository.getAllDocuments(Teacher.class);
            if (documents == null || documents.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            for (DocumentSnapshot document : documents) {
                Teacher teacher = document.toObject(Teacher.class);
                list.add(teacher);
                
                for (int i = 0; i < teacher.getCourseID().size(); i++){
                    String getid = teacher.getCourseID().get(i);
                    courseController.deleteTeacherinCourse(teacher.getId(), getid);
                }
                
                repository.deleteDocumentById(Teacher.class, document.getId());
            }
            return ResponseEntity.ok().body(list);
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

    @PostMapping("/add/id")
    public ResponseEntity<Teacher> add(@RequestParam String id, 
                                        @RequestParam String name, 
                                        @RequestParam String email,
                                        @RequestParam (required = false, defaultValue = "0355916621") String phonenumber,
                                        @RequestParam (required = false, defaultValue = "0000") Integer year,
                                        @RequestParam (required = false, defaultValue =  "01") Integer month,
                                        @RequestParam (required = false, defaultValue = "01") Integer day,
                                        @RequestParam (required = false, defaultValue = "00") Integer hour,
                                        @RequestParam (required = false, defaultValue = "00") Integer minute,
                                        @RequestParam (required = false, defaultValue = "00") Integer second,
                                        @RequestParam (required = false, defaultValue = "null") String master,
                                        @RequestParam (required = false, defaultValue = "null") String phd,
                                        @RequestParam (required = false, defaultValue = "null") String university
                                        ) {
        try{

            DocumentSnapshot snapshot = repository.getDocumentById(Teacher.class, id);
            if (snapshot != null) 
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            Teacher teacher = new Teacher();
            teacher.setId(id);
            teacher.setName(name);
            teacher.setEmail(email);
            Timestamp value = convertTimestamp(year, month, day, hour, minute, second);
            teacher.setDayofBirth(value);
            teacher.setCourseID(new ArrayList<String>());
            teacher.setCertificate(new Certificate(master, phd, university));
            teacher.setPhonenumber(phonenumber);
            repository.saveDocument(teacher, id);
            return ResponseEntity.ok().body(teacher);

        }catch (Exception e){
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
}
