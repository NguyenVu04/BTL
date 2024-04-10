package com.project.backend.Course.Quizz;

import com.google.cloud.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quizz {
    private Timestamp Startdate;
    private Timestamp Enddate;
    private QuestionAndAnswer questionAndAnswer;
    private String title;
    // find score of student by id
    private QuizzDetail quizzDetail;
    protected Quizz(){}

    public Quizz(
        Timestamp startDate,
        Timestamp endDate,
        QuestionAndAnswer questionAndAnswer,
        QuizzDetail quizzDetail,
        String title
        ){
            this.Startdate = startDate;
            this.Enddate = endDate;
            this.questionAndAnswer = questionAndAnswer;
            this.quizzDetail = quizzDetail;
            this.title = title;
        }
}

