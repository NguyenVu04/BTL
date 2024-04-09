package com.project.backend.Teacher;

import java.util.List;

import com.google.cloud.Timestamp;
import com.project.backend.Course.Course;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CollectionName("Teacher")
public class Teacher extends Model {
    private String name;

    private String email;

    private Timestamp dayofBirth;

    private String phonenumber;
    private List<Course> CourseID;   
    private Certificate certificate;

    public Teacher(
                String name, 
                String email, 
                Timestamp dayofBirth, 
                String phonenumber, 
                List<Course> CourseID, 
                Certificate certificate) {
        this.name = name;
        this.email = email;
        this.dayofBirth = dayofBirth;
        this.phonenumber = phonenumber;
        this.CourseID = CourseID;
        this.certificate = certificate;
    }
}
