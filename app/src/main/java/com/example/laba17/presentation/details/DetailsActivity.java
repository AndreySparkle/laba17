package com.example.laba17.presentation.details;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.laba17.R;
import com.example.laba17.data.LocalProductRepository;
import com.example.laba17.domain.model.Product;
import com.example.laba17.presentation.components.ProductAdapter;

import java.util.List;
import java.util.Locale;

/**
 * Purpose: Displays swipeable product details with expandable description.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: showProduct updates details; toggleDescription expands or collapses text.
 */
public class DetailsActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "product_id";

    private LocalProductRepository repository;
    private ProductAdapter adapter;
    private RecyclerView productsView;
    private SnapHelper snapHelper;
    private TextView title;
    private TextView price;
    private TextView description;
    private Button descriptionButton;
    private boolean descriptionExpanded;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        repository = LocalProductRepository.getInstance(this);
        title = findViewById(R.id.detailsTitle);
        price = findViewById(R.id.detailsPrice);
        description = findViewById(R.id.detailsDescription);
        descriptionButton = findViewById(R.id.descriptionButton);
        productsView = findViewById(R.id.detailsProducts);

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
                    updateFromSnappedCard();
                }
            }
        });

        descriptionButton.setOnClickListener(view -> toggleDescription());
        findViewById(R.id.detailsBackButton).setOnClickListener(view -> finish());
        findViewById(R.id.detailsCartButton).setOnClickListener(view -> {
            if (currentProduct != null) {
                repository.addToCart(currentProduct.getId());
                refreshCards();
                Toast.makeText(this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            }
        });

        List<Product> products = repository.getProducts();
        adapter.submitList(products);
        int requestedId = getIntent().getIntExtra(PRODUCT_ID, products.get(0).getId());
        int position = findPosition(products, requestedId);
        productsView.scrollToPosition(position);
        productsView.post(() -> {
            adapter.setSelectedPosition(position);
            showProduct(adapter.getItem(position));
        });
    }

    private void updateFromSnappedCard() {
        RecyclerView.LayoutManager manager = productsView.getLayoutManager();
        if (manager == null) {
            return;
        }
        View snapped = snapHelper.findSnapView(manager);
        if (snapped != null) {
            int position = manager.getPosition(snapped);
            adapter.setSelectedPosition(position);
            showProduct(adapter.getItem(position));
        }
    }

    private void showProduct(Product product) {
        currentProduct = product;
        descriptionExpanded = false;
        title.setText(product.getTitle());
        price.setText(String.format(Locale.getDefault(), "%.0f ₽", product.getPrice()));
        description.setText(product.getDescription());
        description.setMaxLines(2);
        description.setEllipsize(android.text.TextUtils.TruncateAt.END);
        descriptionButton.setText(R.string.more);
    }

    private void toggleDescription() {
        descriptionExpanded = !descriptionExpanded;
        description.setMaxLines(descriptionExpanded ? Integer.MAX_VALUE : 2);
        description.setEllipsize(descriptionExpanded
                ? null
                : android.text.TextUtils.TruncateAt.END);
        descriptionButton.setText(descriptionExpanded ? R.string.less : R.string.more);
    }

    private void refreshCards() {
        int selected = currentProduct == null ? 0 : findPosition(
                repository.getProducts(),
                currentProduct.getId()
        );
        adapter.submitList(repository.getProducts());
        adapter.setSelectedPosition(selected);
    }

    private int findPosition(List<Product> products, int productId) {
        for (int index = 0; index < products.size(); index++) {
            if (products.get(index).getId() == productId) {
                return index;
            }
        }
        return 0;
    }

    private ProductAdapter.Listener createListener() {
        return new ProductAdapter.Listener() {
            @Override
            public void onOpen(Product product) {
                showProduct(product);
            }

            @Override
            public void onFavorite(Product product) {
                repository.toggleFavorite(product.getId());
                refreshCards();
            }

            @Override
            public void onCart(Product product) {
                repository.addToCart(product.getId());
                refreshCards();
                Toast.makeText(DetailsActivity.this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
