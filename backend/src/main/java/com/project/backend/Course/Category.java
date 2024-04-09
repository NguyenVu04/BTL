package com.project.backend.Course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private String major;
    private Integer yearvalid;

    protected Category(){
    }

    public Category(String major, Integer yearvalid) {
        this.major = major;
        this.yearvalid = yearvalid;
    }
}
