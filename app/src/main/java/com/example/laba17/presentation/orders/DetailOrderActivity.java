package com.example.laba17.presentation.orders;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba17.R;
import com.example.laba17.data.LocalOrderRepository;
import com.example.laba17.domain.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Purpose: Displays complete order information and all ordered products.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: onCreate finds the selected order and binds its details.
 */
public class DetailOrderActivity extends AppCompatActivity {

    public static final String ORDER_ID = "order_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        int orderId = getIntent().getIntExtra(ORDER_ID, -1);
        Order order = LocalOrderRepository.getInstance(this).findById(orderId);
        if (order == null) {
            finish();
            return;
        }

        ((TextView) findViewById(R.id.detailOrderNumber)).setText(getString(
                R.string.order_number_format,
                order.getId()
        ));
        ((TextView) findViewById(R.id.detailOrderStatus)).setText(order.getStatus());
        ((TextView) findViewById(R.id.detailOrderTotal)).setText(getString(
                R.string.money_format,
                order.getTotal()
        ));
        ((TextView) findViewById(R.id.detailOrderDate)).setText(
                new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                        .format(new Date(order.getCreatedAtMillis()))
        );

        RecyclerView products = findViewById(R.id.detailOrderProducts);
        products.setLayoutManager(new LinearLayoutManager(this));
        products.setAdapter(new OrderItemsAdapter(order.getItems()));
        products.setNestedScrollingEnabled(false);
        findViewById(R.id.detailOrderBack).setOnClickListener(view -> finish());
    }
}
