package com.project.backend.Course.Quizz;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAndAnswer {
    Integer Score;
    List<String> Questions;

    protected QuestionAndAnswer() {}
    public QuestionAndAnswer(Integer score, List<String> questions) {
        Score = score;
        Questions = questions;
    }

}
