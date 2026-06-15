package com.example.laba17.common;

import android.app.Activity;
import android.content.Intent;

/**
 * Purpose: Keeps simple Activity transitions in one reusable place.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: openAndFinish opens a destination and closes the current screen.
 */
public final class NavigationHelper {

    private NavigationHelper() {
    }

    public static void openAndFinish(Activity source, Class<? extends Activity> destination) {
        source.startActivity(new Intent(source, destination));
        source.finish();
    }
}
