package com.example.trillionaire.models;

import java.util.Objects;

public class Answer {
    public String text;
    private boolean correct;

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(text, answer.text);
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                ", correct=" + correct +
                '}';
    }
}
