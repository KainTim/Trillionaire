package com.example.trillionaire.models;

import com.example.trillionaire.enums.Difficulty;
import com.example.trillionaire.enums.QuestionType;

import java.util.Arrays;

public class StringQuestion {
    public String type;
    public String difficulty;
    public String category;
    public String question;
    public String correct_answer;
    public String[] incorrect_answers;

    public StringQuestion(String type, String difficulty, String category, String question, String correct_answer, String[] incorrect_answers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", category='" + category + '\'' +
                ", question='" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + Arrays.toString(incorrect_answers) +
                '}';
    }
    public Question convertToQuestion(){
        return new Question(QuestionType.valueOf(type.toUpperCase()), Difficulty.valueOf(difficulty.toUpperCase()),category,question,correct_answer,Arrays.asList(incorrect_answers));
    }
}
