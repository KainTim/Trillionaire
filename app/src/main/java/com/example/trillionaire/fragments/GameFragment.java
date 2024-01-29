package com.example.trillionaire.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trillionaire.R;
import com.example.trillionaire.databinding.FragmentGameBinding;
import com.example.trillionaire.enums.QuestionType;
import com.example.trillionaire.models.Answer;
import com.example.trillionaire.models.Question;
import com.example.trillionaire.viewmodels.MainViewModel;

import java.util.Collections;
import java.util.List;

public class GameFragment extends Fragment implements View.OnClickListener {
    private FragmentGameBinding binding;
    private MainViewModel viewModel;
    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (viewModel.questionType.equals(QuestionType.DOUBLE)){
                initDoubleQuestion(viewModel.questions.get(viewModel.questionIndex),viewModel.questions.get(viewModel.questionIndex+1));
            viewModel.questionIndex+=2;
        }else {
            initQuestion(viewModel.questions.get(viewModel.questionIndex));
            viewModel.questionIndex++;
        }
        binding.btnBack.setOnClickListener(this);
        initQuestionCount(viewModel.questionIndex, viewModel.questions.size());
        return binding.getRoot();
    }

    private void initQuestionCount(int count, int of) {
        binding.tvGameCounter.setText(count+"/"+of);
    }

    private void initQuestion(Question question) {
        binding.llQuestions.removeAllViewsInLayout();
        initQuestionText(question);
        initAnswers(question.correct_answer,question.incorrect_answers);
    }

    private void initDoubleQuestion(Question question1, Question question2) {
        binding.llQuestions.removeAllViewsInLayout();
        initQuestionText(question1);
        initQuestionText(question2);
        initDoubleAnswers(question1,question2);

    }

    private void initDoubleAnswers(Question question1, Question question2) {
        viewModel.answerContainers.clear();
        Answer correctAnswer;
        ArrayList<Answer> wrongAnswers = new ArrayList<>();
        if (Math.random()<0.5){
            correctAnswer = question1.correct_answer;
            wrongAnswers.add(question2.incorrect_answers.get((int)(Math.random()*question2.incorrect_answers.size())));
        }else{
            correctAnswer = question2.correct_answer;
            wrongAnswers.add(question1.incorrect_answers.get((int)(Math.random()*question1.incorrect_answers.size())));
        }
        initAnswers(correctAnswer,wrongAnswers);
    }

    private void initQuestionText(Question question) {
        CardView cardView = new CardView(requireContext());
        cardView.setRadius(10);
        TextView textView = new TextView(requireContext());
        textView.setSingleLine(false);
        textView.setPadding(25,25,25,25);
        textView.setText(question.questionText);
        cardView.addView(textView);
        cardView.setPadding(0,25,0,25);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Resources r = requireContext().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                25,
                r.getDisplayMetrics()
        );
        params.bottomMargin = px;
        cardView.setLayoutParams(params);
        binding.llQuestions.addView(cardView);
    }

    private void initAnswers(Answer correctAnswer, List<Answer> incorrectAnswers) {
        viewModel.answerContainers.clear();
        binding.llAnswers.removeAllViewsInLayout();
        ArrayList<Answer> answers = new ArrayList<>(incorrectAnswers);
        answers.add(correctAnswer);
        if (viewModel.questionType==QuestionType.BOOLEAN){
            answers.sort((o1, o2) -> {
                if (o1.getText().equalsIgnoreCase("True")) {
                    return -1;
                }
                return 1;
            });
        }else {
            Collections.shuffle(answers);
        }
        viewModel.currentAnswers.clear();
        viewModel.currentAnswers.addAll(answers);
        for (Answer answer : answers) {
            initAnswer(answer);
        }
    }

    private void initAnswer(Answer answer) {
        CardView cardView = new CardView(requireContext());
        cardView.setRadius(10);
        cardView.setOnClickListener(this);
        viewModel.answerContainers.add(cardView);
        TextView textView = new TextView(requireContext());
        textView.setSingleLine(false);
        textView.setPadding(25,25,25,25);
        textView.setText(answer.text);
        cardView.addView(textView);
        cardView.setPadding(0,25,0,25);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Resources r = requireContext().getResources();
        params.bottomMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                25,
                r.getDisplayMetrics()
        );
        cardView.setLayoutParams(params);
        binding.llAnswers.addView(cardView);

    }

    @Override
    public void onClick(View v) {
        if (viewModel.answerContainers.contains(v)){
            CardView cardView = (CardView) v;
            viewModel.answerContainers.indexOf(v);
            TextView tvText = (TextView) (cardView).getChildAt(0);
            if (viewModel.currentAnswers.contains(new Answer(tvText.getText().toString(),false))){
                int index = viewModel.currentAnswers.indexOf(new Answer(tvText.getText().toString(), false));
                Answer answer = viewModel.currentAnswers.get(index);
                if (answer.isCorrect()){
                    viewModel.isCorrect = true;
                    viewModel.rightAnswers++;
                }else {
                    viewModel.isCorrect = false;
                    viewModel.wrongAnswers++;
                }
                viewModel.showContinue();
            }
        }
        if (v.getId() == R.id.btnBack){
            viewModel.init();

        }
    }
}