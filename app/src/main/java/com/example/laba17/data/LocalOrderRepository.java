package com.example.laba17.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.laba17.domain.OrderRepository;
import com.example.laba17.domain.model.Order;
import com.example.laba17.domain.model.OrderItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Purpose: Supplies local test orders and persists changed order statuses.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getInstance returns shared data; repeatOrder and cancelOrder update local state.
 */
public class LocalOrderRepository extends OrderRepository {

    private static final String STORAGE = "order_storage";
    private static final String STATUS_PREFIX = "status_";
    private static LocalOrderRepository instance;

    private final SharedPreferences preferences;

    private LocalOrderRepository(Context context) {
        super(createOrders());
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        restoreStatuses();
    }

    public static synchronized LocalOrderRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LocalOrderRepository(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public Order repeatOrder(int orderId, long nowMillis) {
        Order repeated = super.repeatOrder(orderId, nowMillis);
        if (repeated != null) {
            preferences.edit()
                    .putString(STATUS_PREFIX + repeated.getId(), repeated.getStatus())
                    .apply();
        }
        return repeated;
    }

    @Override
    public boolean cancelOrder(int orderId) {
        boolean changed = super.cancelOrder(orderId);
        if (changed) {
            preferences.edit()
                    .putString(STATUS_PREFIX + orderId, STATUS_CANCELLED)
                    .apply();
        }
        return changed;
    }

    private void restoreStatuses() {
        for (Order order : getOrders()) {
            String status = preferences.getString(STATUS_PREFIX + order.getId(), order.getStatus());
            order.setStatus(status);
        }
    }

    private static List<Order> createOrders() {
        long now = System.currentTimeMillis();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(
                101,
                now - 12 * 60_000L,
                "Processing",
                Arrays.asList(
                        new OrderItem(1, "Air Run", 1, 4590),
                        new OrderItem(4, "Daily Soft", 2, 3890)
                )
        ));
        orders.add(new Order(
                100,
                now - 26 * 60 * 60_000L,
                "Delivered",
                Arrays.asList(new OrderItem(2, "City Walk", 1, 5290))
        ));
        orders.add(new Order(
                99,
                now - 8 * 24 * 60 * 60_000L,
                "Delivered",
                Arrays.asList(new OrderItem(3, "Blue Speed", 1, 6790))
        ));
        return orders;
    }
}
