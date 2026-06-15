package com.example.laba17.presentation.popular;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.laba17.R;
import com.example.laba17.common.BottomNavigationHelper;
import com.example.laba17.common.ProductFilterIntent;
import com.example.laba17.data.LocalProductRepository;
import com.example.laba17.domain.model.Product;
import com.example.laba17.domain.model.ProductFilter;
import com.example.laba17.presentation.common.BaseDrawerActivity;
import com.example.laba17.presentation.components.ProductAdapter;
import com.example.laba17.presentation.details.DetailsActivity;
import com.example.laba17.presentation.filters.FiltersActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Purpose: Displays a swipeable RecyclerView of popular products and applies filters.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: refreshProducts applies filters; updateSelectedCard highlights snapped item.
 */
public class PopularActivity extends BaseDrawerActivity {

    private static final int FILTER_REQUEST = 301;

    private LocalProductRepository repository;
    private ProductAdapter adapter;
    private ProductFilter activeFilter = new ProductFilter();
    private RecyclerView productsView;
    private SnapHelper snapHelper;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        repository = LocalProductRepository.getInstance(this);
        productsView = findViewById(R.id.popularProducts);
        emptyView = findViewById(R.id.emptyPopular);
        productsView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        ));
        adapter = new ProductAdapter(createListener(), true);
        productsView.setAdapter(adapter);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(productsView);
        productsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    updateSelectedCard();
                }
            }
        });

        findViewById(R.id.openFiltersButton).setOnClickListener(view -> {
            Intent intent = new Intent(this, FiltersActivity.class);
            ProductFilterIntent.write(intent, activeFilter);
            startActivityForResult(intent, FILTER_REQUEST);
        });
        BottomNavigationHelper.setup(
                this,
                (BottomNavigationView) findViewById(R.id.bottomNavigation),
                R.id.navigation_popular
        );
        refreshProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            refreshProducts();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILTER_REQUEST && resultCode == RESULT_OK && data != null) {
            activeFilter = ProductFilterIntent.read(data);
            refreshProducts();
        }
    }

    private void refreshProducts() {
        adapter.submitList(repository.filter(activeFilter));
        boolean empty = adapter.getItemCount() == 0;
        productsView.setVisibility(empty ? View.GONE : View.VISIBLE);
        emptyView.setVisibility(empty ? View.VISIBLE : View.GONE);
        productsView.post(() -> {
            if (adapter.getItemCount() > 0) {
                adapter.setSelectedPosition(0);
                productsView.scrollToPosition(0);
            }
        });
    }

    private void updateSelectedCard() {
        RecyclerView.LayoutManager manager = productsView.getLayoutManager();
        if (manager == null) {
            return;
        }
        View snapped = snapHelper.findSnapView(manager);
        if (snapped != null) {
            adapter.setSelectedPosition(manager.getPosition(snapped));
        }
    }

    private ProductAdapter.Listener createListener() {
        return new ProductAdapter.Listener() {
            @Override
            public void onOpen(Product product) {
                Intent intent = new Intent(PopularActivity.this, DetailsActivity.class);
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
                Toast.makeText(PopularActivity.this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
