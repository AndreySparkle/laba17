package com.example.laba17.presentation.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laba17.R;

/**
 * Purpose: Displays the placeholder Home screen after successful sign-in.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onCreate attaches the Home layout.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
