package com.example.trillionaire.models;

import android.os.Build;

import com.example.trillionaire.enums.Difficulty;
import com.example.trillionaire.enums.QuestionType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Question(QuestionType.valueOf(new String(Base64.getDecoder().decode(type), StandardCharsets.UTF_8).toUpperCase()),
                    Difficulty.valueOf(new String(Base64.getDecoder().decode(difficulty), StandardCharsets.UTF_8).toUpperCase()),
                    new String(Base64.getDecoder().decode(category), StandardCharsets.UTF_8),
                    new String(Base64.getDecoder().decode(question), StandardCharsets.UTF_8),
                    new Answer(new String(Base64.getDecoder().decode(correct_answer), StandardCharsets.UTF_8),true),
                    Arrays.asList(decodeArray(incorrect_answers)));
        }
        return null;
    }
    private Answer[] decodeArray(String[] encoded){
        Answer[] decoded = new Answer[encoded.length];
        for (int i = 0; i < encoded.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                decoded[i]=new Answer (new String(Base64.getDecoder().decode(encoded[i]), StandardCharsets.UTF_8),false);
            }
        }
        return decoded;
    }
}
