package com.example.laba17.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Purpose: Persists the local signed-in state without a server.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: login, logout and isLoggedIn manage the stored session flag.
 */
public class LocalSessionRepository {

    private static final String STORAGE = "session_storage";
    private static final String LOGGED_IN = "logged_in";
    private final SharedPreferences preferences;

    public LocalSessionRepository(Context context) {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public void login() {
        preferences.edit().putBoolean(LOGGED_IN, true).apply();
    }

    public void logout() {
        preferences.edit().putBoolean(LOGGED_IN, false).apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(LOGGED_IN, false);
    }
}
