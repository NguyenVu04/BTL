package com.project.backend.Student;

import java.util.List;

import com.project.backend.Course.Course;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.DOB;
import com.project.backend.model.Model;

import lombok.Getter;
import lombok.Setter;

@CollectionName("Student")

@Getter
@Setter
public class Student extends Model {
    private String name;
    private DOB dob;
    private String email;
    private List<Course> CourseID;
    private boolean status;


    protected Student() {}

    public Student(String name, DOB dob, String email, List<Course> CourseID, boolean status) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.CourseID = CourseID;
        this.status = false;
    }
}
