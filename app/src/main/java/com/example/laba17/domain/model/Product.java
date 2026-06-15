package com.example.laba17.domain.model;

/**
 * Purpose: Describes a locally stored shoe product and its user state.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getters expose product data; setters update favorite and cart state.
 */
public class Product {

    private final int id;
    private final String title;
    private final String description;
    private final double price;
    private final int imageResId;
    private boolean favorite;
    private boolean inCart;
    private final String color;
    private final String category;
    private final int discount;

    public Product(
            int id,
            String title,
            String description,
            double price,
            int imageResId,
            boolean favorite,
            boolean inCart,
            String color,
            String category,
            int discount
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.favorite = favorite;
        this.inCart = inCart;
        this.color = color;
        this.category = category;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public String getColor() {
        return color;
    }

    public String getCategory() {
        return category;
    }

    public int getDiscount() {
        return discount;
    }
}
