package com.example.laba17.domain.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Purpose: Carries optional product filtering parameters between screens.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getters and setters configure price, discount, color and category filters.
 */
public class ProductFilter {

    private Double minPrice;
    private Double maxPrice;
    private boolean discountedOnly;
    private final Set<String> colors = new HashSet<>();
    private final Set<String> categories = new HashSet<>();

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isDiscountedOnly() {
        return discountedOnly;
    }

    public void setDiscountedOnly(boolean discountedOnly) {
        this.discountedOnly = discountedOnly;
    }

    public Set<String> getColors() {
        return colors;
    }

    public Set<String> getCategories() {
        return categories;
    }
}
