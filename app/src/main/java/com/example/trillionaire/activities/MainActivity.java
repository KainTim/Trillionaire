package com.example.trillionaire.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.trillionaire.R;
import com.example.trillionaire.fragments.ConfigFragment;
import com.example.trillionaire.fragments.ContinueFragment;
import com.example.trillionaire.fragments.GameFragment;
import com.example.trillionaire.fragments.MenuFragment;
import com.example.trillionaire.fragments.ResultFragment;
import com.example.trillionaire.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.requestQueue = Volley.newRequestQueue(getApplicationContext());
        viewModel.state.observe(this, state -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);
            switch (state){
                case 1:
                    transaction.replace(R.id.clMain, MenuFragment.newInstance());
                    break;
                case 2:
                    transaction.replace(R.id.clMain, ConfigFragment.newInstance());
                    break;
                case 3:
                    transaction.replace(R.id.clMain, GameFragment.newInstance());
                    break;
                case 4:
                    transaction.replace(R.id.clMain, ContinueFragment.newInstance());
                    break;
                case 5:
                    transaction.replace(R.id.clMain, ResultFragment.newInstance());
                    break;
                default:
            }
            transaction.commit();
        });
        setContentView(R.layout.activity_main);
    }
}