package com.example.laba17.domain;

import com.example.laba17.domain.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Purpose: Provides testable local order repeat, cancel and grouping operations.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: repeatOrder, cancelOrder, groupByDate and findById.
 */
public class OrderRepository {

    public static final String STATUS_CREATED = "Created";
    public static final String STATUS_CANCELLED = "Cancelled";

    private final List<Order> orders;

    public OrderRepository(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        List<Order> result = new ArrayList<>(orders);
        result.sort((first, second) ->
                Long.compare(second.getCreatedAtMillis(), first.getCreatedAtMillis()));
        return result;
    }

    public Order findById(int orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public Order repeatOrder(int orderId, long nowMillis) {
        Order source = findById(orderId);
        if (source == null) {
            return null;
        }
        Order repeated = source.copyWith(nextId(), nowMillis, STATUS_CREATED);
        orders.add(repeated);
        return repeated;
    }

    public boolean cancelOrder(int orderId) {
        Order order = findById(orderId);
        if (order == null) {
            return false;
        }
        order.setStatus(STATUS_CANCELLED);
        return true;
    }

    public Map<String, List<Order>> groupByDate(Locale locale) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", locale);
        Map<String, List<Order>> result = new LinkedHashMap<>();
        for (Order order : getOrders()) {
            String date = formatter.format(new Date(order.getCreatedAtMillis()));
            if (!result.containsKey(date)) {
                result.put(date, new ArrayList<>());
            }
            result.get(date).add(order);
        }
        return result;
    }

    private int nextId() {
        int maximum = 0;
        for (Order order : orders) {
            maximum = Math.max(maximum, order.getId());
        }
        return maximum + 1;
    }
}
