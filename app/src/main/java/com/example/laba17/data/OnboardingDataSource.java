package com.example.laba17.data;

import android.content.Context;

import com.example.laba17.R;
import com.example.laba17.domain.model.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Creates the local onboarding content from Android resources.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getItems returns all pages in display order.
 */
public class OnboardingDataSource {

    public List<OnboardingItem> getItems(Context context) {
        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem(
                R.drawable.onboarding_one,
                context.getString(R.string.onboarding_title_one),
                context.getString(R.string.onboarding_description_one)
        ));
        items.add(new OnboardingItem(
                R.drawable.onboarding_two,
                context.getString(R.string.onboarding_title_two),
                context.getString(R.string.onboarding_description_two)
        ));
        items.add(new OnboardingItem(
                R.drawable.onboarding_three,
                context.getString(R.string.onboarding_title_three),
                context.getString(R.string.onboarding_description_three)
        ));
        return items;
    }
}
