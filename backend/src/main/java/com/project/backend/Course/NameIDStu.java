package com.project.backend.Course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class NameIDStu {
    String email;
    String name;
    String id;
    Double midTerm;
    Double finalExam;
    Double assignment;
    Double other;
    protected NameIDStu(){}

    public NameIDStu(String name, String id, Double midTerm, Double finalExam, Double assignment, Double other, String email) {
        this.name = name;
        this.id = id;
        this.midTerm = midTerm;
        this.finalExam = finalExam;
        this.assignment = assignment;
        this.other = other;
        this.email = email;
    }
}
