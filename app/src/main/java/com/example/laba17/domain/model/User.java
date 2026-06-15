package com.example.laba17.domain.model;

/**
 * Purpose: Stores editable local profile and loyalty card data.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getters and setters expose and update profile fields.
 */
public class User {

    private String name;
    private String phone;
    private String email;
    private String photoUri;
    private final String loyaltyCardNumber;

    public User(String name, String phone, String email, String photoUri, String loyaltyCardNumber) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.photoUri = photoUri;
        this.loyaltyCardNumber = loyaltyCardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getLoyaltyCardNumber() {
        return loyaltyCardNumber;
    }
}
