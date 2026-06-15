package com.example.laba17.presentation.onboarding;

import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laba17.R;
import com.example.laba17.common.NavigationHelper;
import com.example.laba17.data.OnboardingDataSource;
import com.example.laba17.domain.model.OnboardingItem;
import com.example.laba17.presentation.signin.SignInActivity;

import java.util.List;

/**
 * Purpose: Displays three swipeable onboarding pages with animated content.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: showPage updates content; animateToPage applies fade animation;
 * handleAction processes the button; onTouchEvent handles horizontal swipes.
 */
public class OnboardingActivity extends AppCompatActivity {

    private static final int SWIPE_DISTANCE = 100;
    private static final int SWIPE_VELOCITY = 100;

    private ImageView imageView;
    private TextView titleView;
    private TextView descriptionView;
    private LinearLayout contentContainer;
    private LinearLayout indicatorsContainer;
    private Button actionButton;
    private List<OnboardingItem> items;
    private int currentPage;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        imageView = findViewById(R.id.onboardingImage);
        titleView = findViewById(R.id.onboardingTitle);
        descriptionView = findViewById(R.id.onboardingDescription);
        contentContainer = findViewById(R.id.onboardingContent);
        indicatorsContainer = findViewById(R.id.indicatorsContainer);
        actionButton = findViewById(R.id.onboardingActionButton);

        items = new OnboardingDataSource().getItems(this);
        gestureDetector = new GestureDetector(this, new SwipeListener());
        contentContainer.setOnTouchListener((view, event) -> gestureDetector.onTouchEvent(event));
        actionButton.setOnClickListener(view -> handleAction());

        createIndicators();
        showPage(0);
    }

    private void handleAction() {
        if (currentPage == 0 || currentPage == items.size() - 1) {
            NavigationHelper.openAndFinish(this, SignInActivity.class);
        } else {
            animateToPage(currentPage + 1);
        }
    }

    private void animateToPage(int page) {
        if (page < 0 || page >= items.size() || page == currentPage) {
            return;
        }
        contentContainer.animate()
                .alpha(0f)
                .setDuration(180)
                .withEndAction(() -> {
                    showPage(page);
                    contentContainer.animate().alpha(1f).setDuration(180).start();
                })
                .start();
    }

    private void showPage(int page) {
        currentPage = page;
        OnboardingItem item = items.get(page);
        imageView.setImageResource(item.getImageResId());
        titleView.setText(item.getTitle());
        descriptionView.setText(item.getDescription());

        if (page == 0) {
            actionButton.setText(R.string.skip);
        } else if (page == items.size() - 1) {
            actionButton.setText(R.string.sign_in);
        } else {
            actionButton.setText(R.string.next);
        }
        updateIndicators();
    }

    private void createIndicators() {
        int size = getResources().getDimensionPixelSize(R.dimen.indicator_size);
        int margin = getResources().getDimensionPixelSize(R.dimen.indicator_margin);
        for (int index = 0; index < items.size(); index++) {
            View indicator = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(margin, 0, margin, 0);
            indicator.setLayoutParams(params);
            indicator.setBackgroundResource(R.drawable.indicator_inactive);
            indicatorsContainer.addView(indicator);
        }
    }

    private void updateIndicators() {
        for (int index = 0; index < indicatorsContainer.getChildCount(); index++) {
            View indicator = indicatorsContainer.getChildAt(index);
            indicator.setBackgroundResource(index == currentPage
                    ? R.drawable.indicator_active
                    : R.drawable.indicator_inactive);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class SwipeListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(@NonNull MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(
                MotionEvent first,
                @NonNull MotionEvent second,
                float velocityX,
                float velocityY
        ) {
            if (first == null) {
                return false;
            }
            float distance = second.getX() - first.getX();
            if (Math.abs(distance) < SWIPE_DISTANCE || Math.abs(velocityX) < SWIPE_VELOCITY) {
                return false;
            }
            animateToPage(distance < 0 ? currentPage + 1 : currentPage - 1);
            return true;
        }
    }
}
