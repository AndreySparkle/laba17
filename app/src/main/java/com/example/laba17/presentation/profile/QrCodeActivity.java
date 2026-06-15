package com.example.laba17.presentation.profile;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laba17.R;

/**
 * Purpose: Shows the loyalty QR code full screen with 75 percent brightness.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onCreate configures brightness and QR content.
 */
public class QrCodeActivity extends AppCompatActivity {

    public static final String CARD_NUMBER = "card_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.screenBrightness = 0.75f;
        getWindow().setAttributes(attributes);
        setContentView(R.layout.activity_qr_code);

        QrCodeView qrCode = findViewById(R.id.fullQrCode);
        qrCode.setValue(getIntent().getStringExtra(CARD_NUMBER));
        findViewById(R.id.closeQrButton).setOnClickListener(view -> finish());
    }
}
