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

    protected Quizz(){
    }

    public Quizz(
        Timestamp startDate,
        Timestamp endDate,
        QuestionAndAnswer questionAndAnswer
        ){
            this.Startdate = startDate;
            this.Enddate = endDate;
            this.questionAndAnswer = questionAndAnswer;
        }
}

