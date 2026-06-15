package com.example.laba17.domain.model;

/**
 * Purpose: Describes one product line inside an order.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: getters expose product title, quantity and unit price.
 */
public class OrderItem {

    private final int productId;
    private final String title;
    private final int quantity;
    private final double unitPrice;

    public OrderItem(int productId, String title, int quantity, double unitPrice) {
        this.productId = productId;
        this.title = title;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }
}
