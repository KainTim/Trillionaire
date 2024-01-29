package com.example.trillionaire.enums;

import androidx.annotation.NonNull;

public enum QuestionType {
    MULTIPLE{
        @NonNull
        @Override
        public String toString() {
            return "Multiple Choice";
        }
    },BOOLEAN{
        @NonNull
        @Override
        public String toString() {
            return "True/False";
        }
    },DOUBLE{
        @NonNull
        @Override
        public String toString() {
            return "2 Questions 2 Answers";
        }
    };
    public static QuestionType convertToEnum(String s){
        switch (s){
            case "Multiple Choice":
                return QuestionType.MULTIPLE;
            case "True/False":
                return QuestionType.BOOLEAN;
            case "2 Questions 2 Answers":
                return QuestionType.DOUBLE;
        }
        return null;
    }

}
