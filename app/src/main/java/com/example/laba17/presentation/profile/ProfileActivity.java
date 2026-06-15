package com.example.laba17.presentation.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.laba17.R;
import com.example.laba17.data.LocalUserRepository;
import com.example.laba17.domain.model.User;
import com.example.laba17.presentation.common.BaseDrawerActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Purpose: Displays and edits the local user profile and loyalty QR card.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: showEditDialog updates fields; showPhotoSourceDialog selects photo source.
 */
public class ProfileActivity extends BaseDrawerActivity {

    private static final int REQUEST_CAMERA = 501;
    private static final int REQUEST_GALLERY = 502;

    private LocalUserRepository repository;
    private User user;
    private ImageView photo;
    private TextView name;
    private TextView phone;
    private TextView email;
    private QrCodeView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        repository = new LocalUserRepository(this);
        photo = findViewById(R.id.profilePhoto);
        name = findViewById(R.id.profileName);
        phone = findViewById(R.id.profilePhone);
        email = findViewById(R.id.profileEmail);
        qrCode = findViewById(R.id.profileQr);

        findViewById(R.id.editProfileButton).setOnClickListener(view -> showEditDialog());
        findViewById(R.id.changePhotoButton).setOnClickListener(view -> showPhotoSourceDialog());
        qrCode.setOnClickListener(view -> {
            Intent intent = new Intent(this, QrCodeActivity.class);
            intent.putExtra(QrCodeActivity.CARD_NUMBER, user.getLoyaltyCardNumber());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = repository.getUser();
        bindUser();
    }

    private void bindUser() {
        name.setText(user.getName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        qrCode.setValue(user.getLoyaltyCardNumber());
        if (!user.getPhotoUri().isEmpty()) {
            try {
                photo.setImageURI(Uri.parse(user.getPhotoUri()));
            } catch (RuntimeException exception) {
                photo.setImageResource(R.drawable.ic_profile_large);
            }
        } else {
            photo.setImageResource(R.drawable.ic_profile_large);
        }
    }

    private void showEditDialog() {
        View content = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null);
        EditText nameInput = content.findViewById(R.id.editName);
        EditText phoneInput = content.findViewById(R.id.editPhone);
        EditText emailInput = content.findViewById(R.id.editEmail);
        nameInput.setText(user.getName());
        phoneInput.setText(user.getPhone());
        emailInput.setText(user.getEmail());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.edit_profile)
                .setView(content)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.save, null)
                .create();
        dialog.setOnShowListener(ignored -> dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(view -> {
                    String newName = nameInput.getText().toString().trim();
                    String newPhone = phoneInput.getText().toString().trim();
                    String newEmail = emailInput.getText().toString().trim();
                    if (newName.isEmpty() || newPhone.isEmpty() || newEmail.isEmpty()) {
                        Toast.makeText(this, R.string.profile_fields_required, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user.setName(newName);
                    user.setPhone(newPhone);
                    user.setEmail(newEmail);
                    repository.saveUser(user);
                    bindUser();
                    dialog.dismiss();
                }));
        dialog.show();
    }

    private void showPhotoSourceDialog() {
        String[] options = {
                getString(R.string.camera),
                getString(R.string.gallery),
                getString(R.string.kandinsky)
        };
        new AlertDialog.Builder(this)
                .setTitle(R.string.choose_photo)
                .setItems(options, (dialog, index) -> {
                    if (index == 0) {
                        startActivityForResult(
                                new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                                REQUEST_CAMERA
                        );
                    } else if (index == 1) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.setType("image/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, REQUEST_GALLERY);
                    } else {
                        Toast.makeText(this, R.string.kandinsky_stub, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_GALLERY && data.getData() != null) {
            Uri uri = data.getData();
            try {
                getContentResolver().takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
            } catch (SecurityException ignored) {
            }
            user.setPhotoUri(uri.toString());
            repository.saveUser(user);
            bindUser();
        } else if (requestCode == REQUEST_CAMERA && data.getExtras() != null) {
            Object image = data.getExtras().get("data");
            if (image instanceof Bitmap) {
                saveCameraBitmap((Bitmap) image);
            }
        }
    }

    private void saveCameraBitmap(Bitmap bitmap) {
        File file = new File(getFilesDir(), "profile_photo.jpg");
        try (FileOutputStream output = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output);
            user.setPhotoUri(Uri.fromFile(file).toString());
            repository.saveUser(user);
            bindUser();
        } catch (IOException exception) {
            Toast.makeText(this, R.string.photo_save_error, Toast.LENGTH_SHORT).show();
        }
    }
}
