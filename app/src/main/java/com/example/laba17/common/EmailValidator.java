package com.example.laba17.common;

import java.util.regex.Pattern;

/**
 * Purpose: Validates e-mail addresses required by the sign-in screen.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: isValid checks the complete address against the lab pattern.
 */
public final class EmailValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$");

    private EmailValidator() {
    }

    public static boolean isValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
