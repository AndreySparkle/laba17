package com.example.laba17.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Stores local order content, creation time and current status.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getTotal calculates cost; copyWith creates repeated orders.
 */
public class Order {

    private final int id;
    private final long createdAtMillis;
    private String status;
    private final List<OrderItem> items;

    public Order(int id, long createdAtMillis, String status, List<OrderItem> items) {
        this.id = id;
        this.createdAtMillis = createdAtMillis;
        this.status = status;
        this.items = new ArrayList<>(items);
    }

    public int getId() {
        return id;
    }

    public long getCreatedAtMillis() {
        return createdAtMillis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public Order copyWith(int newId, long newCreatedAt, String newStatus) {
        return new Order(newId, newCreatedAt, newStatus, items);
    }
}
