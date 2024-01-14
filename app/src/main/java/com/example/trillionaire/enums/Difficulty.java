package com.example.trillionaire.enums;

import androidx.annotation.NonNull;

public enum Difficulty {
    EASY{
        @NonNull
        @Override
        public String toString() {
            return "Easy";
        }
    }, MEDIUM{
        @NonNull
        @Override
        public String toString() {
            return "Medium";
        }
    },HARD{
        @NonNull
        @Override
        public String toString() {
            return "Hard";
        }
    }
}
