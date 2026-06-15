package com.example.laba17.domain;

import com.example.laba17.domain.model.Product;
import com.example.laba17.domain.model.ProductFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Provides testable local product operations and filtering.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: toggleFavorite, addToCart, filter, getFavorites and findById.
 */
public class ProductRepository {

    private final List<Product> products;

    public ProductRepository(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public Product findById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void toggleFavorite(int productId) {
        Product product = findById(productId);
        if (product != null) {
            product.setFavorite(!product.isFavorite());
        }
    }

    public void addToCart(int productId) {
        Product product = findById(productId);
        if (product != null) {
            product.setInCart(true);
        }
    }

    public List<Product> getFavorites() {
        List<Product> favorites = new ArrayList<>();
        for (Product product : products) {
            if (product.isFavorite()) {
                favorites.add(product);
            }
        }
        return favorites;
    }

    public List<Product> filter(ProductFilter filter) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (matches(product, filter)) {
                result.add(product);
            }
        }
        return result;
    }

    private boolean matches(Product product, ProductFilter filter) {
        if (filter.getMinPrice() != null && product.getPrice() < filter.getMinPrice()) {
            return false;
        }
        if (filter.getMaxPrice() != null && product.getPrice() > filter.getMaxPrice()) {
            return false;
        }
        if (filter.isDiscountedOnly() && product.getDiscount() <= 0) {
            return false;
        }
        if (!filter.getColors().isEmpty() && !filter.getColors().contains(product.getColor())) {
            return false;
        }
        return filter.getCategories().isEmpty()
                || filter.getCategories().contains(product.getCategory());
    }
}
