package com.example.trillionaire.models;

import com.example.trillionaire.enums.Difficulty;
import com.example.trillionaire.enums.QuestionType;

import java.util.List;

public class Question {
    public QuestionType type;
    public Difficulty difficulty;
    public String category;
    public String questionText;
    public String correct_answer;
    public List<String> incorrect_answers;
    public Question(QuestionType type, Difficulty difficulty, String category, String questionText, String correct_answer, List<String> incorrect_answers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.questionText = questionText;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "type=" + type +
                ", difficulty=" + difficulty +
                ", category='" + category + '\'' +
                ", questionText='" + questionText + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + incorrect_answers +
                '}';
    }
}