package com.example.trillionaire.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trillionaire.databinding.FragmentMenuBinding;
import com.example.trillionaire.models.Question;
import com.example.trillionaire.models.StringQuestion;
import com.example.trillionaire.viewmodels.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuFragment extends Fragment implements View.OnClickListener {
    private FragmentMenuBinding binding;
    MainViewModel viewModel;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
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
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        binding.btnRequest.setOnClickListener(this);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://opentdb.com/api.php?amount=1&difficulty=medium";
         JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Response: " ,response.toString()+"\n\n"+response.names());
                        try {
                            JSONArray results = response.getJSONArray("results");
                            TypeToken<StringQuestion> typeToken = TypeToken.get(StringQuestion.class);
                            Gson gson = new Gson();
                            Question question = gson.fromJson(results.get(0).toString(), typeToken).convertToQuestion();
                            Log.d("", question.toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, error -> binding.tvResult.setText(error.toString()));
        queue.add(request);
        viewModel.showConfig();
    }

}