package com.example.laba17.domain.model;

/**
 * Purpose: Stores the image, title and description of one onboarding page.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getters provide immutable page data.
 */
public class OnboardingItem {

    private final int imageResId;
    private final String title;
    private final String description;

    public OnboardingItem(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
