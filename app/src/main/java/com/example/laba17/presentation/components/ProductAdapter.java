package com.example.laba17.presentation.components;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Displays ProductCard instances in vertical and horizontal RecyclerViews.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onBindViewHolder binds actions; submitList refreshes products;
 * setSelectedPosition highlights the current product.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface Listener {
        void onOpen(Product product);

        void onFavorite(Product product);

        void onCart(Product product);
    }

    private final List<Product> products = new ArrayList<>();
    private final Listener listener;
    private final boolean horizontal;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ProductAdapter(Listener listener, boolean horizontal) {
        this.listener = listener;
        this.horizontal = horizontal;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductCard card = new ProductCard(parent.getContext());
        int width = horizontal
                ? parent.getResources().getDimensionPixelSize(R.dimen.product_card_width)
                : ViewGroup.LayoutParams.MATCH_PARENT;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                width,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        int margin = parent.getResources().getDimensionPixelSize(R.dimen.product_card_margin);
        params.setMargins(margin, margin, margin, margin);
        card.setLayoutParams(params);
        return new ProductViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.card.bind(
                product,
                view -> listener.onOpen(product),
                view -> listener.onFavorite(product),
                view -> listener.onCart(product)
        );
        holder.card.setSelectedState(selectedPosition == RecyclerView.NO_POSITION
                || selectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void submitList(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);
        if (selectedPosition >= products.size()) {
            selectedPosition = RecyclerView.NO_POSITION;
        }
        notifyDataSetChanged();
    }

    public Product getItem(int position) {
        return products.get(position);
    }

    public void setSelectedPosition(int position) {
        int previous = selectedPosition;
        selectedPosition = position;
        if (previous != RecyclerView.NO_POSITION) {
            notifyItemChanged(previous);
        }
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position);
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ProductCard card;

        ProductViewHolder(ProductCard card) {
            super(card);
            this.card = card;
        }
    }
}
