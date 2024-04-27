package com.project.backend.Course;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NameIDStu {
    String name;
    String id;
    Double midTerm;
    Double finalExam;
    Double assignment;
    protected NameIDStu(){}

    public NameIDStu(String name, String id, Double midTerm, Double finalExam, Double assignment) {
        this.name = name;
        this.id = id;
        this.midTerm = midTerm;
        this.finalExam = finalExam;
        this.assignment = assignment;
    }
}
