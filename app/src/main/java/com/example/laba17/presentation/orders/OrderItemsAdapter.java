package com.example.laba17.presentation.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.domain.model.OrderItem;

import java.util.List;

/**
 * Purpose: Displays product lines with quantity and cost in order details.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onBindViewHolder binds item title, quantity and total price.
 */
public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.Holder> {

    private final List<OrderItem> items;

    public OrderItemsAdapter(List<OrderItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        OrderItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.quantity.setText(holder.itemView.getContext().getString(
                R.string.quantity_format,
                item.getQuantity()
        ));
        holder.price.setText(holder.itemView.getContext().getString(
                R.string.money_format,
                item.getTotalPrice()
        ));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView quantity;
        private final TextView price;

        Holder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.orderItemTitle);
            quantity = itemView.findViewById(R.id.orderItemQuantity);
            price = itemView.findViewById(R.id.orderItemPrice);
        }
    }
}
