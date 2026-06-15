package com.example.laba17.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.common.BottomNavigationHelper;
import com.example.laba17.data.LocalProductRepository;
import com.example.laba17.domain.model.Product;
import com.example.laba17.presentation.common.BaseDrawerActivity;
import com.example.laba17.presentation.components.ProductAdapter;
import com.example.laba17.presentation.details.DetailsActivity;
import com.example.laba17.presentation.popular.PopularActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Purpose: Displays featured local products and the main bottom navigation.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onCreate configures content; refreshProducts updates product state.
 */
public class HomeActivity extends BaseDrawerActivity {

    private LocalProductRepository repository;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        repository = LocalProductRepository.getInstance(this);
        RecyclerView products = findViewById(R.id.homeProducts);
        products.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        ));
        adapter = new ProductAdapter(createListener(), true);
        products.setAdapter(adapter);

        findViewById(R.id.showPopularButton).setOnClickListener(view ->
                startActivity(new Intent(this, PopularActivity.class)));
        BottomNavigationHelper.setup(
                this,
                (BottomNavigationView) findViewById(R.id.bottomNavigation),
                R.id.navigation_home
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshProducts();
    }

    private void refreshProducts() {
        adapter.submitList(repository.getProducts());
    }

    private ProductAdapter.Listener createListener() {
        return new ProductAdapter.Listener() {
            @Override
            public void onOpen(Product product) {
                Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.PRODUCT_ID, product.getId());
                startActivity(intent);
            }

            @Override
            public void onFavorite(Product product) {
                repository.toggleFavorite(product.getId());
                refreshProducts();
            }

            @Override
            public void onCart(Product product) {
                repository.addToCart(product.getId());
                refreshProducts();
                Toast.makeText(HomeActivity.this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
