package com.example.trillionaire.viewmodels;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;

import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trillionaire.enums.Difficulty;
import com.example.trillionaire.enums.QuestionType;
import com.example.trillionaire.models.Answer;
import com.example.trillionaire.models.Category;
import com.example.trillionaire.models.Question;
import com.example.trillionaire.models.StringQuestion;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public static final int SHOW_MENU = 1;
    public static final int SHOW_CONFIG = 2;
    public static final int SHOW_GAME = 3;
    public static final int SHOW_CONTINUE = 4;
    private static final int SHOW_RESULT = 5;
    public boolean isCorrect;
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
    public void showContinue(){
        _state.postValue(SHOW_CONTINUE);
    }
    public void showResult() {
        _state.postValue(SHOW_RESULT);
    }
    public List<Category> categories = new ArrayList<>();
    public List<Question> questions = new ArrayList<>();
    public Category selectedCategory;
    public QuestionType questionType;
    public Difficulty difficulty = null;
    public List<Answer> currentAnswers = new ArrayList<>();
    public List<CardView> answerContainers = new ArrayList<>();
    public RequestQueue requestQueue;
    private MutableLiveData<Integer> _requestState = new MutableLiveData<>();
    public LiveData<Integer> requestState = _requestState;
    public int questionIndex;
    public int wrongAnswers = 0;
    public int rightAnswers = 1;
    private MutableLiveData<Integer> _cgState = new MutableLiveData<>(0);
    public LiveData<Integer> cgState = _cgState;
    public void requestQuestions(String url){
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    //Log.d("Response: " ,response.toString()+"\n\n"+response.names());
                    try {
                        JSONArray results = response.getJSONArray("results");
                        TypeToken<StringQuestion> typeToken = TypeToken.get(StringQuestion.class);
                        Gson gson = new Gson();
                        List<Question> tmp = new ArrayList<>();
                        for (int i = 0; i < results.length() ; i++) {
                            Question question = gson.fromJson(results.get(i).toString(), typeToken).convertToQuestion();
                            tmp.add(question);
                            Log.d("", question.toString());
                        }
                        questions.clear();
                        questions.addAll(tmp);
                        if (questions.size()>0){
                            _requestState.postValue(1);
                        }else{
                            Log.e("QuestionError", "No Questions");
                            _requestState.postValue(2);
                        }
                    } catch (JSONException e) {
                        Log.e("QuestionError", e.getMessage());
                        _requestState.postValue(3);
                        requestQuestions(url);
                    }
                }, error -> {
                    Log.e("QuestionError", error.toString());
                    _requestState.postValue(3);
                    if (error.networkResponse.statusCode==429){
                        Log.e("QuestionError", "Rate Limit");
                        new Thread(() -> {
                            try {
                                Thread.sleep(5000);
                                requestQuestions(url);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    }else {
                        requestQuestions("https://opentdb.com/api.php?amount="+10+"&encode=base64");
                    }});
        requestQueue.add(request);
    }
    public void requestCategories(){
        String url = "https://opentdb.com/api_category.php";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        Log.d("", response.toString());
                        JSONArray results = response.getJSONArray("trivia_categories");
                        Log.d("", results.toString());
                        for (int i = 0; i < results.length(); i++) {
                            String name = results.getJSONObject(i).getString("name");
                            int id = results.getJSONObject(i).getInt("id");
                            categories.add(new Category(name,id));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    _cgState.postValue(1);
                }, error -> Log.e("API ERROR", error.toString()));
        requestQueue.add(request);
    }

    public void init() {
        isCorrect = false;
        categories.clear();
        questions.clear();
        selectedCategory = null;
        questionType = null;
        difficulty = null;
        currentAnswers.clear();
        answerContainers.clear();
        requestQueue.cancelAll(request -> true);
        _requestState.postValue(0);
        questionIndex = 0;
        wrongAnswers = 0;
        rightAnswers = 1;
        showConfig();
    }
}
