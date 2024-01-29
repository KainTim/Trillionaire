package com.example.trillionaire.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trillionaire.R;
import com.example.trillionaire.databinding.FragmentResultBinding;
import com.example.trillionaire.viewmodels.MainViewModel;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ResultFragment extends Fragment {
    private FragmentResultBinding binding;
    private MainViewModel viewModel;
    public ResultFragment() {
        // Required empty public constructor
    }
    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
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
        binding = FragmentResultBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        int rightAnswers = viewModel.rightAnswers;
        int wrongAnswers = viewModel.wrongAnswers;
        StringBuilder stringBuilder = new StringBuilder("https://quickchart.io/chart=%7Bhttps://quickchart.io/chart?width=300&height=400&chart={type%3A%20%27bar%27%2C%0Adata%3A%20{labels%3A%20%0A[%27%27]%2C%0Adatasets%3A%20[%0A{label%3A%20%27Right%20Answers%27%2C%0AbackgroundColor%3A%20%27rgba(0%2C%20255%2C%200%2C%200.7)%27%2C%0Adata%3A%20[");
        stringBuilder.append(rightAnswers)
                .append("]%2C}%2C%0A{label%3A%20%27Wrong%20Answers%27%2C%0AbackgroundColor%3A%20%27rgba(255%2C%200%2C%200%2C%200.7)%27%2C%0Adata%3A%20[")
                .append(wrongAnswers)
                .append("]%2C}%0A%2C]%0A%2C}%2Coptions%3A{legend%3A{display%3Afalse}}}");
        Picasso.get().load(stringBuilder.toString()).into(binding.imageView);
        return binding.getRoot();
    }
}