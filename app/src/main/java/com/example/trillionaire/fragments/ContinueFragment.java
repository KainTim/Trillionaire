package com.example.trillionaire.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trillionaire.R;
import com.example.trillionaire.databinding.FragmentContinueBinding;
import com.example.trillionaire.enums.QuestionType;
import com.example.trillionaire.viewmodels.MainViewModel;

public class ContinueFragment extends Fragment implements View.OnClickListener {
    private MainViewModel viewModel;

    public ContinueFragment() {
        // Required empty public constructor
    }

    public static ContinueFragment newInstance() {
        ContinueFragment fragment = new ContinueFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
        //    mParam1 = getArguments().getString(ARG_PARAM1);
        //    mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentContinueBinding binding = FragmentContinueBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.cvContinue.setCardBackgroundColor(viewModel.isCorrect? Color.GREEN :Color.RED);
        binding.tvContinue.setText(viewModel.isCorrect?"Correct":"Incorrect");
        binding.btnContinue.setOnClickListener(this);
        binding.tvContinueCount.setText(viewModel.questionIndex+"/"+viewModel.questions.size());
        binding.tvCorrectAnswer.setText(viewModel.questions.get(viewModel.questionIndex-1).correct_answer.getText());
        binding.btnContinueBack.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (R.id.btnContinue ==v.getId()){
            if (viewModel.questionIndex>=viewModel.questions.size()||(viewModel.questionType== QuestionType.DOUBLE&&viewModel.questionIndex>=viewModel.questions.size()-1)){
                viewModel.showResult();
            }else {
                viewModel.showGame();
            }
        }
        if (R.id.btnContinueBack == v.getId()){
            viewModel.init();
        }
    }
}