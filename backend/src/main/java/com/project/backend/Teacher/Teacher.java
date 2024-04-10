package com.project.backend.Teacher;

import java.util.List;

import com.google.cloud.Timestamp;
import com.project.backend.Course.Course;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@CollectionName("Teacher")
@NoArgsConstructor
public class Teacher extends Model {
    private String name;

    private String email;

    private Timestamp dayofBirth;

    private String phonenumber;
    private List<String> CourseID;   
    private Certificate certificate;

    
    public Teacher(
                String name, 
                String email, 
                Timestamp dayofBirth, 
                String phonenumber, 
                List<String> CourseID, 
                Certificate certificate) {
        this.name = name;
        this.email = email;
        this.dayofBirth = dayofBirth;
        this.phonenumber = phonenumber;
        this.CourseID = CourseID;
        this.certificate = certificate;
    }
}
