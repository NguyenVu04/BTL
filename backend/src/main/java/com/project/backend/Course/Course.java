package com.project.backend.Course;

import java.util.List;
import java.util.Map;

import com.google.cloud.Timestamp;
import com.project.backend.QuizMain.Quizz;
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
    private Timestamp startDate;;
    private Timestamp endDate;;
    private List<Lesson> LessonMaterials;
    private Double price;
    private Map<String,String> timeTable;

    private List<String> listStudent;
    private List<String> listTeacher;

    // multiple quizzes
    private List<Quizz> listQuizz;
    protected Course(){}

    public Course(
            String name, 
            Category category, 
            Timestamp startDate, 
            Timestamp endDate, 
            List<Lesson> LessonMaterials, 
            Double price, 
            Map<String,String> timeTable,
            List<String> listStudent,
            List<String> listTeacher,
            List<Quizz> listQuizz
        ) {
        this.name = name;
        this.category = category;
        this.endDate = endDate;
        this.startDate = startDate;
        this.LessonMaterials = LessonMaterials;
        this.price = price;
        this.timeTable = timeTable;
        this.listStudent = listStudent;
        this.listTeacher = listTeacher;
        this.listQuizz = listQuizz;
    }
}
