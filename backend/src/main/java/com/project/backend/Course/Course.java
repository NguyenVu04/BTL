package com.project.backend.Course;

import java.util.List;
import java.util.Map;

import com.google.cloud.Timestamp;
import com.project.backend.Teacher.Teacher;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CollectionName("Course")
public class Course extends Model{
    private String name; // name of the course
    private Category category;
    private Timestamp endDate;;
    private Timestamp startDate;;
    private List<Lesson> LessonMaterials;
    private Integer price;
    private List<Quizz> quizz;
    private List<String> TeacherID;
    private Map<String,String> timeTable;


    protected Course(){}

    public Course(String name, Category category, Timestamp endDate, Timestamp startDate, List<Lesson> LessonMaterials, Integer price, List<Quizz> quizz, List<String> TeacherID, Map<String,String> timeTable) {
        this.name = name;
        this.category = category;
        this.endDate = endDate;
        this.startDate = startDate;
        this.LessonMaterials = LessonMaterials;
        this.price = price;
        this.quizz = quizz;
        this.TeacherID = TeacherID;
        this.timeTable = timeTable;
    }
}
