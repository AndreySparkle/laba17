package com.example.laba17.domain;

import com.example.laba17.domain.model.Product;
import com.example.laba17.domain.model.ProductFilter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Purpose: Verifies product state changes and all required filtering rules.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: tests cover favorites, cart, price, discount and shoe category.
 */
public class ProductRepositoryTest {

    private ProductRepository repository;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<>(Arrays.asList(
                product(1, 3200, "Black", "Sport", 10),
                product(2, 5100, "White", "Casual", 0),
                product(3, 7400, "Blue", "Sport", 20)
        ));
        repository = new ProductRepository(products);
    }

    @Test
    public void toggleFavorite_addsProductToFavorites() {
        repository.toggleFavorite(1);

        assertTrue(repository.findById(1).isFavorite());
        assertEquals(1, repository.getFavorites().size());
    }

    @Test
    public void addToCart_marksProductAsInCart() {
        assertFalse(repository.findById(2).isInCart());

        repository.addToCart(2);

        assertTrue(repository.findById(2).isInCart());
    }

    @Test
    public void filter_returnsProductsInsidePriceRange() {
        ProductFilter filter = new ProductFilter();
        filter.setMinPrice(4000.0);
        filter.setMaxPrice(6000.0);

        List<Product> result = repository.filter(filter);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId());
    }

    @Test
    public void filter_returnsOnlyDiscountedProducts() {
        ProductFilter filter = new ProductFilter();
        filter.setDiscountedOnly(true);

        List<Product> result = repository.filter(filter);

        assertEquals(2, result.size());
        assertTrue(result.get(0).getDiscount() > 0);
        assertTrue(result.get(1).getDiscount() > 0);
    }

    @Test
    public void filter_returnsSelectedShoeCategory() {
        ProductFilter filter = new ProductFilter();
        filter.getCategories().add("Casual");

        List<Product> result = repository.filter(filter);

        assertEquals(1, result.size());
        assertEquals("Casual", result.get(0).getCategory());
    }

    private Product product(int id, double price, String color, String category, int discount) {
        return new Product(
                id,
                "Product " + id,
                "Description",
                price,
                0,
                false,
                false,
                color,
                category,
                discount
        );
    }
}
