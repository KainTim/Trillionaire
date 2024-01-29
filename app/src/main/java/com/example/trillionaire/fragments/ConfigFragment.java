package com.example.trillionaire.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trillionaire.R;
import com.example.trillionaire.databinding.FragmentConfigBinding;
import com.example.trillionaire.enums.Difficulty;
import com.example.trillionaire.enums.QuestionType;
import com.example.trillionaire.models.Category;
import com.example.trillionaire.models.Question;
import com.example.trillionaire.models.StringQuestion;
import com.example.trillionaire.viewmodels.MainViewModel;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

public class ConfigFragment extends Fragment implements View.OnClickListener{
    private FragmentConfigBinding binding;
    private MainViewModel viewModel;

    public ConfigFragment() {
        // Required empty public constructor
    }

    public static ConfigFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return new ConfigFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfigBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        initNetworkCgs();
        initOfflineCgs();
        binding.btnStart.setOnClickListener(this);
        binding.btnStart.setText("Start");
        binding.btnStart.setBackgroundColor(Color.GRAY);
        viewModel.requestState.observe(getViewLifecycleOwner(),integer -> {
            if (integer==0){

            }else if (integer==1){
                binding.btnStart.setBackgroundColor(Color.GRAY);
                binding.btnStart.setText("Success...");
                viewModel.showGame();
            }else if (integer==2){
                binding.btnStart.setBackgroundColor(Color.RED);
                binding.btnStart.setText("No Questions!");
            }else if (integer==3){
                binding.btnStart.setBackgroundColor(Color.RED);
                binding.btnStart.setText("RETRYING...");
            }
        });
        return binding.getRoot();
    }

    private void initOfflineCgs() {
        for (Difficulty value : Difficulty.values()) {
            Chip chip = new Chip(requireContext());
            chip.setText(value.toString());
            chip.setId(ViewCompat.generateViewId());
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) viewModel.difficulty = Difficulty.valueOf(buttonView.getText().toString().toUpperCase());
            });
            binding.cgDifficulty.addView(chip);
        }
        for (QuestionType value : QuestionType.values()) {
            Chip chip = new Chip(requireContext());
            chip.setText(value.toString());
            chip.setId(ViewCompat.generateViewId());
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) viewModel.questionType = QuestionType.convertToEnum(buttonView.getText().toString().toUpperCase());
            });
            binding.cgType.addView(chip);
        }
    }

    private void initNetworkCgs() {
        viewModel.requestCategories();
        viewModel.cgState.observe(getViewLifecycleOwner(),integer -> {
            if (integer== 0) return;
            for (int i = 0; i < viewModel.categories.size(); i++) {
                Chip chip = new Chip(requireContext());
                chip.setText(viewModel.categories.get(i).getName());
                chip.setId(ViewCompat.generateViewId());
                chip.setCheckable(true);
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) viewModel.selectedCategory = viewModel.categories.stream()
                            .filter(category -> category.getName().equalsIgnoreCase(chip.getText().toString()))
                            .findFirst()
                            .get();
                });
                binding.cgCategories.addView(chip);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (R.id.btnStart == v.getId()) {
            int amount = 25;
            StringBuilder sb = new StringBuilder("https://opentdb.com/api.php");
            sb.append("?amount=").append(amount).append("&encode=base64");
            if (viewModel.selectedCategory!=null) sb.append("&category=").append(viewModel.selectedCategory.getId());
            if (viewModel.difficulty!=null) sb.append("&difficulty=").append(viewModel.difficulty.toString().toLowerCase());
            if (viewModel.questionType!=null) if (viewModel.questionType!=QuestionType.DOUBLE) {
                sb.append("&type=").append(viewModel.questionType.toString().toLowerCase());
            }else if (viewModel.questionType!=null) if (viewModel.questionType==QuestionType.DOUBLE){
                sb.append("&type=").append(QuestionType.MULTIPLE.toString().toLowerCase());
            }
            if (viewModel.questionType==null){
                viewModel.questionType = QuestionType.BOOLEAN;
            }
            String url = sb.toString();
            viewModel.requestQuestions(url);

        }
    }

}