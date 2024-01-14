package com.example.trillionaire.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trillionaire.enums.Difficulty;
import com.example.trillionaire.enums.QuestionType;
import com.example.trillionaire.models.Category;
import com.example.trillionaire.models.Question;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public static final int SHOW_MENU = 1;
    public static final int SHOW_CONFIG = 2;
    public static final int SHOW_GAME = 3;
    private MutableLiveData<Integer> _state = new MutableLiveData<>(SHOW_MENU);
    public LiveData<Integer> state = _state;
    public void showMenu(){
        _state.postValue(SHOW_MENU);
    }
    public void showConfig(){
        _state.postValue(SHOW_CONFIG);
    }
    public void showGame(){
        _state.postValue(SHOW_GAME);
    }
    public List<Category> categories = new ArrayList<>();
    public List<Question> questions = new ArrayList<>();
    public Category selectedCategory;
    public QuestionType questionType;
    public Difficulty difficulty = null;
}
