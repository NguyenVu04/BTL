package com.project.backend.Course;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NameIDStu {
    String name;
    String id;

    protected NameIDStu(){}

    public NameIDStu(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
