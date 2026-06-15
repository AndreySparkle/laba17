package com.example.laba17.presentation.signin;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laba17.R;
import com.example.laba17.common.EmailValidator;
import com.example.laba17.common.NavigationHelper;
import com.example.laba17.presentation.home.HomeActivity;

/**
 * Purpose: Collects and validates user sign-in data.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: validateAndSignIn validates fields; togglePasswordVisibility
 * switches password display; showError displays a user-dismissed dialog.
 */
public class SignInActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private ImageButton passwordToggle;
    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordToggle = findViewById(R.id.passwordToggle);

        passwordToggle.setOnClickListener(view -> togglePasswordVisibility());
        findViewById(R.id.signInButton).setOnClickListener(view -> validateAndSignIn());
    }

    private void validateAndSignIn() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            showError(getString(R.string.error_empty_fields));
            return;
        }
        if (!EmailValidator.isValid(email)) {
            showError(getString(R.string.error_invalid_email));
            return;
        }
        NavigationHelper.openAndFinish(this, HomeActivity.class);
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        int inputType = InputType.TYPE_CLASS_TEXT
                | (passwordVisible
                ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordInput.setInputType(inputType);
        passwordInput.setSelection(passwordInput.length());
        passwordToggle.setImageResource(passwordVisible
                ? R.drawable.ic_visibility_off
                : R.drawable.ic_visibility);
        passwordToggle.setContentDescription(getString(passwordVisible
                ? R.string.hide_password
                : R.string.show_password));
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
