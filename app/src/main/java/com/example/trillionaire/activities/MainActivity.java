package com.example.trillionaire.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.trillionaire.R;
import com.example.trillionaire.fragments.ConfigFragment;
import com.example.trillionaire.fragments.GameFragment;
import com.example.trillionaire.fragments.MenuFragment;
import com.example.trillionaire.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.state.observe(this, state -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (state){
                case 1:
                    transaction.replace(R.id.clMain, MenuFragment.newInstance());
                    break;
                case 2:
                    transaction.replace(R.id.clMain, ConfigFragment.newInstance())
                            .addToBackStack(null);
                    break;
                case 3:
                    transaction.replace(R.id.clMain, GameFragment.newInstance())
                            .addToBackStack(null);
                    break;
                default:
            }
            transaction.commit();
        });
        setContentView(R.layout.activity_main);
    }
}