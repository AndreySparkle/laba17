package com.example.laba17.presentation.orders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.data.LocalOrderRepository;
import com.example.laba17.domain.model.Order;
import com.example.laba17.presentation.common.BaseDrawerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Purpose: Displays orders grouped by date and handles repeat or cancel swipes.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: refreshOrders rebuilds groups; handleSwipe repeats right and cancels left.
 */
public class OrdersActivity extends BaseDrawerActivity {

    private LocalOrderRepository repository;
    private OrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        repository = LocalOrderRepository.getInstance(this);
        RecyclerView list = findViewById(R.id.ordersList);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrdersAdapter(order -> {
            Intent intent = new Intent(this, DetailOrderActivity.class);
            intent.putExtra(DetailOrderActivity.ORDER_ID, order.getId());
            startActivity(intent);
        });
        list.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public int getSwipeDirs(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder
            ) {
                return adapter.getOrder(viewHolder.getBindingAdapterPosition()) == null
                        ? 0
                        : super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                handleSwipe(viewHolder.getBindingAdapterPosition(), direction);
            }
        });
        helper.attachToRecyclerView(list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshOrders();
    }

    private void handleSwipe(int position, int direction) {
        Order order = adapter.getOrder(position);
        if (order == null) {
            refreshOrders();
            return;
        }
        if (direction == ItemTouchHelper.RIGHT) {
            repository.repeatOrder(order.getId(), System.currentTimeMillis());
            Toast.makeText(this, R.string.order_repeated, Toast.LENGTH_SHORT).show();
        } else {
            repository.cancelOrder(order.getId());
            Toast.makeText(this, R.string.order_cancelled, Toast.LENGTH_SHORT).show();
        }
        refreshOrders();
    }

    private void refreshOrders() {
        List<OrderListEntry> entries = new ArrayList<>();
        for (Map.Entry<String, List<Order>> group
                : repository.groupByDate(Locale.getDefault()).entrySet()) {
            entries.add(OrderListEntry.header(group.getKey()));
            for (Order order : group.getValue()) {
                entries.add(OrderListEntry.order(order));
            }
        }
        adapter.submitList(entries);
    }
}
