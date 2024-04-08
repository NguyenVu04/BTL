package com.project.backend.Teacher;

import java.util.List;

import com.project.backend.Course.Course;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.DOB;
import com.project.backend.model.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CollectionName("Teacher")
public class Teacher extends Model {
    private String name;

    private String email;

    private DOB dob;

    private String phonenumber;
    private List<Course> CourseID;   
    private Certificate certificate;

    public Teacher(
                String name, 
                String email, 
                DOB dob, 
                String phonenumber, 
                List<Course> CourseID, 
                Certificate certificate) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phonenumber = phonenumber;
        this.CourseID = CourseID;
        this.certificate = certificate;
    }
}
