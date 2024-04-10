package com.project.backend.Course;

import java.util.List;
import java.util.Map;

import com.google.cloud.Timestamp;
import com.project.backend.Course.Quizz.Quizz;
import com.project.backend.Student.Student;
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
    private Timestamp startDate;;
    private Timestamp endDate;;
    private List<Lesson> LessonMaterials;
    private Double price;
    private List<Map<String, Quizz>> quizz;
    private Map<String,String> timeTable;

    private List<Student> listStudent;
    private List<Teacher> listTeacher; 
    protected Course(){}

    public Course(
            String name, 
            Category category, 
            Timestamp startDate, 
            Timestamp endDate, 
            List<Lesson> LessonMaterials, 
            Double price, 
            List<Map<String, Quizz>> quizz, 
            Map<String,String> timeTable,
            List<Student> listStudent,
            List<Teacher> listTeacher
            ) {
        this.name = name;
        this.category = category;
        this.endDate = endDate;
        this.startDate = startDate;
        this.LessonMaterials = LessonMaterials;
        this.price = price;
        this.quizz = quizz;
        this.timeTable = timeTable;
        this.listStudent = listStudent;
        this.listTeacher = listTeacher;
    }
}
