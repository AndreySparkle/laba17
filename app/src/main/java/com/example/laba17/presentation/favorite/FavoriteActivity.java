package com.example.laba17.presentation.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Purpose: Displays products marked as favorite and the shared bottom navigation.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: refreshFavorites reloads local favorites; createListener handles actions.
 */
public class FavoriteActivity extends BaseDrawerActivity {

    private LocalProductRepository repository;
    private ProductAdapter adapter;
    private RecyclerView products;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        repository = LocalProductRepository.getInstance(this);
        products = findViewById(R.id.favoriteProducts);
        emptyView = findViewById(R.id.emptyFavorites);
        products.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(createListener(), false);
        products.setAdapter(adapter);

        BottomNavigationHelper.setup(
                this,
                (BottomNavigationView) findViewById(R.id.bottomNavigation),
                R.id.navigation_favorite
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshFavorites();
    }

    private void refreshFavorites() {
        adapter.submitList(repository.getFavorites());
        boolean empty = adapter.getItemCount() == 0;
        products.setVisibility(empty ? View.GONE : View.VISIBLE);
        emptyView.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    private ProductAdapter.Listener createListener() {
        return new ProductAdapter.Listener() {
            @Override
            public void onOpen(Product product) {
                Intent intent = new Intent(FavoriteActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.PRODUCT_ID, product.getId());
                startActivity(intent);
            }

            @Override
            public void onFavorite(Product product) {
                repository.toggleFavorite(product.getId());
                refreshFavorites();
            }

            @Override
            public void onCart(Product product) {
                repository.addToCart(product.getId());
                refreshFavorites();
                Toast.makeText(FavoriteActivity.this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
