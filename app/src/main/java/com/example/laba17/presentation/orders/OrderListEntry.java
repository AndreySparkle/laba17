package com.example.laba17.presentation.orders;

import com.example.laba17.domain.model.Order;

/**
 * Purpose: Represents either a date header or an order row in the grouped list.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: header and order create typed entries; getters expose their content.
 */
public class OrderListEntry {

    private final String header;
    private final Order order;

    private OrderListEntry(String header, Order order) {
        this.header = header;
        this.order = order;
    }

    public static OrderListEntry header(String header) {
        return new OrderListEntry(header, null);
    }

    public static OrderListEntry order(Order order) {
        return new OrderListEntry(null, order);
    }

    public boolean isHeader() {
        return order == null;
    }

    public String getHeader() {
        return header;
    }

    public Order getOrder() {
        return order;
    }
}
