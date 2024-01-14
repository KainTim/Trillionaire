package com.example.trillionaire.models;

public class Answer {
    public String text;
    private boolean correct;

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }
}
