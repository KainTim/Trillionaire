package com.example.trillionaire.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trillionaire.models.StringQuestion;

import java.util.Stack;

public class MainViewModel extends ViewModel {
    public static final int SHOW_MENU = 1;
    public static final int SHOW_CONFIG = 2;
    public static final int SHOW_GAME = 3;
    private MutableLiveData<Integer> _state = new MutableLiveData<>(SHOW_MENU);
    public LiveData<Integer> state = _state;
    private MutableLiveData<Stack<StringQuestion>> _questionStack = new MutableLiveData<>(new Stack<>());
    public LiveData<Stack<StringQuestion>> questionStack = _questionStack;
    public void showMenu(){
        _state.postValue(SHOW_MENU);
    }
    public void showConfig(){
        _state.postValue(SHOW_CONFIG);
    }
    public void showGame(){
        _state.postValue(SHOW_GAME);
    }

}
