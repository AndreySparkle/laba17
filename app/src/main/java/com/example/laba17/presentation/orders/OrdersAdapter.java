package com.example.laba17.presentation.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.common.OrderDateFormatter;
import com.example.laba17.domain.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Purpose: Displays grouped order headers and order rows in one RecyclerView.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: submitList refreshes entries; getOrder returns a swipeable row order.
 */
public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Listener {
        void onOpen(Order order);
    }

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ORDER = 1;

    private final List<OrderListEntry> entries = new ArrayList<>();
    private final Listener listener;

    public OrdersAdapter(Listener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return entries.get(position).isHeader() ? TYPE_HEADER : TYPE_ORDER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            return new HeaderHolder(inflater.inflate(R.layout.item_order_header, parent, false));
        }
        return new OrderHolder(inflater.inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderListEntry entry = entries.get(position);
        if (holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).title.setText(entry.getHeader());
            return;
        }
        Order order = entry.getOrder();
        OrderHolder orderHolder = (OrderHolder) holder;
        orderHolder.number.setText(holder.itemView.getContext().getString(
                R.string.order_number_format,
                order.getId()
        ));
        orderHolder.status.setText(order.getStatus());
        orderHolder.total.setText(holder.itemView.getContext().getString(
                R.string.money_format,
                order.getTotal()
        ));
        orderHolder.date.setText(OrderDateFormatter.format(
                order.getCreatedAtMillis(),
                System.currentTimeMillis(),
                Locale.getDefault()
        ));
        orderHolder.itemView.setOnClickListener(view -> listener.onOpen(order));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void submitList(List<OrderListEntry> newEntries) {
        entries.clear();
        entries.addAll(newEntries);
        notifyDataSetChanged();
    }

    public Order getOrder(int position) {
        if (position < 0 || position >= entries.size() || entries.get(position).isHeader()) {
            return null;
        }
        return entries.get(position).getOrder();
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        HeaderHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.orderGroupTitle);
        }
    }

    static class OrderHolder extends RecyclerView.ViewHolder {

        private final TextView number;
        private final TextView status;
        private final TextView total;
        private final TextView date;

        OrderHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.orderNumber);
            status = itemView.findViewById(R.id.orderStatus);
            total = itemView.findViewById(R.id.orderTotal);
            date = itemView.findViewById(R.id.orderDate);
        }
    }
}
