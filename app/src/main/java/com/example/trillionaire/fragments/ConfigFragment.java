package com.example.trillionaire.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trillionaire.R;
import com.example.trillionaire.databinding.FragmentConfigBinding;
import com.example.trillionaire.enums.Difficulty;
import com.example.trillionaire.enums.QuestionType;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigFragment extends Fragment implements View.OnClickListener {
    private FragmentConfigBinding binding;
    public ConfigFragment() {
        // Required empty public constructor
    }
    public static ConfigFragment newInstance() {
        ConfigFragment fragment = new ConfigFragment();
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
        binding = FragmentConfigBinding.inflate(inflater, container, false);
        initNetworkCgs();
        initOfflineCgs();
        binding.btnStart.setOnClickListener(this);
        return binding.getRoot();
    }

    private void initOfflineCgs() {
        for (Difficulty value : Difficulty.values()) {
            Chip chip = new Chip(requireContext());
            chip.setText(value.toString());
            binding.cgDifficulty.addView(chip);
        }
        for (QuestionType value : QuestionType.values()) {
            Chip chip = new Chip(requireContext());
            chip.setText(value.toString());
            binding.cgType.addView(chip);
        }
    }

    private void initNetworkCgs() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://opentdb.com/api_category.php";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Response: " ,response.toString()+"\n\n"+response.names());
                        try {
                            Log.d("", response.toString());
                            JSONArray results = response.getJSONArray("trivia_categories");
                            Log.d("", results.toString());
                            for (int i = 0; i < results.length(); i++) {
                                Chip chip = new Chip(requireContext());
                                chip.setText(results.getJSONObject(i).getString("name"));
                                binding.cgCategories.addView(chip);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, error -> Log.e("API ERROR", error.toString()));
        queue.add(request);
    }

    @Override
    public void onClick(View v) {
        if (R.id.btnStart==v.getId()){

        }
    }
}