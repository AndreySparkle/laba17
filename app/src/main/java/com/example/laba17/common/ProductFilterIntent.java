package com.example.laba17.common;

import android.content.Intent;

import com.example.laba17.domain.model.ProductFilter;

import java.util.ArrayList;

/**
 * Purpose: Serializes filter parameters through Activity intent extras.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: write stores filter values; read reconstructs a ProductFilter.
 */
public final class ProductFilterIntent {

    public static final String MIN_PRICE = "min_price";
    public static final String MAX_PRICE = "max_price";
    public static final String DISCOUNTED = "discounted";
    public static final String COLORS = "colors";
    public static final String CATEGORIES = "categories";

    private ProductFilterIntent() {
    }

    public static void write(Intent intent, ProductFilter filter) {
        if (filter.getMinPrice() != null) {
            intent.putExtra(MIN_PRICE, filter.getMinPrice());
        }
        if (filter.getMaxPrice() != null) {
            intent.putExtra(MAX_PRICE, filter.getMaxPrice());
        }
        intent.putExtra(DISCOUNTED, filter.isDiscountedOnly());
        intent.putStringArrayListExtra(COLORS, new ArrayList<>(filter.getColors()));
        intent.putStringArrayListExtra(
                CATEGORIES,
                new ArrayList<>(filter.getCategories())
        );
    }

    public static ProductFilter read(Intent intent) {
        ProductFilter filter = new ProductFilter();
        if (intent.hasExtra(MIN_PRICE)) {
            filter.setMinPrice(intent.getDoubleExtra(MIN_PRICE, 0));
        }
        if (intent.hasExtra(MAX_PRICE)) {
            filter.setMaxPrice(intent.getDoubleExtra(MAX_PRICE, 0));
        }
        filter.setDiscountedOnly(intent.getBooleanExtra(DISCOUNTED, false));
        ArrayList<String> colors = intent.getStringArrayListExtra(COLORS);
        ArrayList<String> categories = intent.getStringArrayListExtra(CATEGORIES);
        if (colors != null) {
            filter.getColors().addAll(colors);
        }
        if (categories != null) {
            filter.getCategories().addAll(categories);
        }
        return filter;
    }
}
