package com.example.laba17.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.laba17.R;
import com.example.laba17.domain.ProductRepository;
import com.example.laba17.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Supplies test products and persists favorites and cart state locally.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getInstance returns shared storage; toggleFavorite and addToCart persist changes.
 */
public class LocalProductRepository extends ProductRepository {

    private static final String STORAGE_NAME = "product_storage";
    private static final String FAVORITE_PREFIX = "favorite_";
    private static final String CART_PREFIX = "cart_";
    private static LocalProductRepository instance;

    private final SharedPreferences preferences;

    private LocalProductRepository(Context context) {
        super(createProducts(context.getApplicationContext()));
        preferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        restoreState();
    }

    public static synchronized LocalProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LocalProductRepository(context);
        }
        return instance;
    }

    @Override
    public void toggleFavorite(int productId) {
        super.toggleFavorite(productId);
        Product product = findById(productId);
        if (product != null) {
            preferences.edit()
                    .putBoolean(FAVORITE_PREFIX + productId, product.isFavorite())
                    .apply();
        }
    }

    @Override
    public void addToCart(int productId) {
        super.addToCart(productId);
        Product product = findById(productId);
        if (product != null) {
            preferences.edit().putBoolean(CART_PREFIX + productId, true).apply();
        }
    }

    private void restoreState() {
        for (Product product : getProducts()) {
            product.setFavorite(preferences.getBoolean(
                    FAVORITE_PREFIX + product.getId(),
                    false
            ));
            product.setInCart(preferences.getBoolean(
                    CART_PREFIX + product.getId(),
                    false
            ));
        }
    }

    private static List<Product> createProducts(Context context) {
        List<Product> products = new ArrayList<>();
        products.add(new Product(
                1,
                context.getString(R.string.product_air_run),
                context.getString(R.string.product_air_run_description),
                4590,
                R.drawable.product_shoe_one,
                false,
                false,
                "Black",
                "Sport",
                15
        ));
        products.add(new Product(
                2,
                context.getString(R.string.product_city_walk),
                context.getString(R.string.product_city_walk_description),
                5290,
                R.drawable.product_shoe_two,
                false,
                false,
                "White",
                "Casual",
                0
        ));
        products.add(new Product(
                3,
                context.getString(R.string.product_blue_speed),
                context.getString(R.string.product_blue_speed_description),
                6790,
                R.drawable.product_shoe_three,
                false,
                false,
                "Blue",
                "Sport",
                20
        ));
        products.add(new Product(
                4,
                context.getString(R.string.product_daily_soft),
                context.getString(R.string.product_daily_soft_description),
                3890,
                R.drawable.product_shoe_four,
                false,
                false,
                "Red",
                "Casual",
                10
        ));
        products.add(new Product(
                5,
                context.getString(R.string.product_green_move),
                context.getString(R.string.product_green_move_description),
                7490,
                R.drawable.product_shoe_five,
                false,
                false,
                "Green",
                "Sport",
                0
        ));
        return products;
    }
}
