package com.example.laba17;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laba17.presentation.onboarding.OnboardingActivity;

/**
 * Purpose: Displays the application splash screen.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onCreate starts a delayed transition; onDestroy clears it.
 */
public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MILLIS = 2000L;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable openOnboarding = () -> {
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.postDelayed(openOnboarding, SPLASH_DELAY_MILLIS);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(openOnboarding);
        super.onDestroy();
    }
}
