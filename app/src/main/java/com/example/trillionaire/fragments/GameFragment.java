package com.example.trillionaire.fragments;

import android.content.res.Resources;
import android.os.Bundle;

import java.util.ArrayList;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trillionaire.databinding.FragmentGameBinding;
import com.example.trillionaire.enums.QuestionType;
import com.example.trillionaire.models.Answer;
import com.example.trillionaire.models.Question;
import com.example.trillionaire.viewmodels.MainViewModel;

import java.util.Collections;
import java.util.List;

public class GameFragment extends Fragment {
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
                initDoubleQuestion(viewModel.questions.get(0),viewModel.questions.get(1));
        }else {
            initQuestion(viewModel.questions.get(0));

        }
        return binding.getRoot();
    }

    private void initQuestion(Question question) {
        binding.llQuestions.removeAllViewsInLayout();
        initQuestionText(question);
        initAnswers(question.correct_answer,question.incorrect_answers);
    }

    private void initDoubleQuestion(Question question1, Question question2) {
        binding.llQuestions.removeAllViewsInLayout();
        initQuestionText(question1);

        //initDoubleAnswers();

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
        binding.llAnswers.removeAllViewsInLayout();
        ArrayList<Answer> answers = new ArrayList<>(incorrectAnswers);
        answers.add(correctAnswer);
        Collections.shuffle(answers);
        for (Answer answer : answers) {
            initAnswer(answer);
        }
    }

    private void initAnswer(Answer answer) {
        CardView cardView = new CardView(requireContext());
        cardView.setRadius(10);
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
}