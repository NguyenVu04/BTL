package com.project.backend.Student;

import java.util.List;

import com.google.cloud.Timestamp;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

import lombok.Getter;
import lombok.Setter;

@CollectionName("Student")

@Getter
@Setter
public class Student extends Model {
    private String name;
    private Timestamp dob;
    private String email;
    private List<String> CourseID;
    private boolean status;


    protected Student() {}

    public Student(String name, Timestamp dob, String email, List<String> CourseID, boolean status) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.CourseID = CourseID;
        this.status = false;
    }
}
