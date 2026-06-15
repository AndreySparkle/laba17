package com.example.laba17.presentation.catalog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.common.BottomNavigationHelper;
import com.example.laba17.data.LocalProductRepository;
import com.example.laba17.domain.model.Product;
import com.example.laba17.domain.model.ProductFilter;
import com.example.laba17.presentation.common.BaseDrawerActivity;
import com.example.laba17.presentation.components.ProductAdapter;
import com.example.laba17.presentation.details.DetailsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Purpose: Displays product categories and the products in the selected category.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: selectCategory filters products; createListener handles card actions.
 */
public class CatalogActivity extends BaseDrawerActivity {

    private LocalProductRepository repository;
    private ProductAdapter adapter;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        repository = LocalProductRepository.getInstance(this);
        RecyclerView products = findViewById(R.id.catalogProducts);
        products.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(createListener(), false);
        products.setAdapter(adapter);

        findViewById(R.id.sportCategory).setOnClickListener(view -> selectCategory("Sport"));
        findViewById(R.id.casualCategory).setOnClickListener(view -> selectCategory("Casual"));
        findViewById(R.id.allCategory).setOnClickListener(view -> selectCategory(null));

        BottomNavigationHelper.setup(
                this,
                (BottomNavigationView) findViewById(R.id.bottomNavigation),
                R.id.navigation_catalog
        );
        selectCategory(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectCategory(selectedCategory);
    }

    private void selectCategory(String category) {
        selectedCategory = category;
        ProductFilter filter = new ProductFilter();
        if (category != null) {
            filter.getCategories().add(category);
        }
        adapter.submitList(repository.filter(filter));
    }

    private ProductAdapter.Listener createListener() {
        return new ProductAdapter.Listener() {
            @Override
            public void onOpen(Product product) {
                Intent intent = new Intent(CatalogActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.PRODUCT_ID, product.getId());
                startActivity(intent);
            }

            @Override
            public void onFavorite(Product product) {
                repository.toggleFavorite(product.getId());
                selectCategory(selectedCategory);
            }

            @Override
            public void onCart(Product product) {
                repository.addToCart(product.getId());
                selectCategory(selectedCategory);
                Toast.makeText(CatalogActivity.this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
