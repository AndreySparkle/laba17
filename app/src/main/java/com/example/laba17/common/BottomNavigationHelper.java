package com.example.laba17.common;

import android.app.Activity;
import android.content.Intent;

import com.example.laba17.R;
import com.example.laba17.presentation.catalog.CatalogActivity;
import com.example.laba17.presentation.favorite.FavoriteActivity;
import com.example.laba17.presentation.home.HomeActivity;
import com.example.laba17.presentation.popular.PopularActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Purpose: Configures consistent bottom navigation for product screens.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: setup selects the current item and opens the requested screen.
 */
public final class BottomNavigationHelper {

    private BottomNavigationHelper() {
    }

    public static void setup(Activity activity, BottomNavigationView navigation, int selectedId) {
        navigation.setSelectedItemId(selectedId);
        navigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == selectedId) {
                return true;
            }

            Class<? extends Activity> destination;
            if (itemId == R.id.navigation_home) {
                destination = HomeActivity.class;
            } else if (itemId == R.id.navigation_catalog) {
                destination = CatalogActivity.class;
            } else if (itemId == R.id.navigation_popular) {
                destination = PopularActivity.class;
            } else {
                destination = FavoriteActivity.class;
            }
            Intent intent = new Intent(activity, destination);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            return true;
        });
    }
}
