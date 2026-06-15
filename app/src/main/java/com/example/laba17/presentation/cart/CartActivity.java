package com.example.laba17.presentation.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.data.LocalProductRepository;
import com.example.laba17.domain.model.Product;
import com.example.laba17.presentation.common.BaseDrawerActivity;
import com.example.laba17.presentation.components.ProductAdapter;
import com.example.laba17.presentation.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Displays products that were locally added to the cart.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: refreshCart filters cart products; createListener handles card actions.
 */
public class CartActivity extends BaseDrawerActivity {

    private LocalProductRepository repository;
    private ProductAdapter adapter;
    private RecyclerView list;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        repository = LocalProductRepository.getInstance(this);
        list = findViewById(R.id.cartProducts);
        empty = findViewById(R.id.emptyCart);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(createListener(), false);
        list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCart();
    }

    private void refreshCart() {
        List<Product> products = new ArrayList<>();
        for (Product product : repository.getProducts()) {
            if (product.isInCart()) {
                products.add(product);
            }
        }
        adapter.submitList(products);
        empty.setVisibility(products.isEmpty() ? View.VISIBLE : View.GONE);
        list.setVisibility(products.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private ProductAdapter.Listener createListener() {
        return new ProductAdapter.Listener() {
            @Override
            public void onOpen(Product product) {
                Intent intent = new Intent(CartActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.PRODUCT_ID, product.getId());
                startActivity(intent);
            }

            @Override
            public void onFavorite(Product product) {
                repository.toggleFavorite(product.getId());
                refreshCart();
            }

            @Override
            public void onCart(Product product) {
                Toast.makeText(CartActivity.this, R.string.already_in_cart, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
