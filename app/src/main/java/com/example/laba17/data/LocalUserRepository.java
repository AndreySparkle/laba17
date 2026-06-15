package com.example.laba17.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.laba17.domain.model.User;

/**
 * Purpose: Loads and saves editable user profile data in SharedPreferences.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getUser reads profile data; saveUser persists all editable fields.
 */
public class LocalUserRepository {

    private static final String STORAGE = "user_storage";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String PHOTO = "photo";
    private static final String CARD = "card";

    private final SharedPreferences preferences;

    public LocalUserRepository(Context context) {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public User getUser() {
        return new User(
                preferences.getString(NAME, "Alex User"),
                preferences.getString(PHONE, "+7 900 123-45-67"),
                preferences.getString(EMAIL, "test123@mail.ru"),
                preferences.getString(PHOTO, ""),
                preferences.getString(CARD, "1700 2026 0001")
        );
    }

    public void saveUser(User user) {
        preferences.edit()
                .putString(NAME, user.getName())
                .putString(PHONE, user.getPhone())
                .putString(EMAIL, user.getEmail())
                .putString(PHOTO, user.getPhotoUri())
                .putString(CARD, user.getLoyaltyCardNumber())
                .apply();
    }
}
