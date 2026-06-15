package com.example.laba17.presentation.filters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laba17.R;
import com.example.laba17.common.ProductFilterIntent;
import com.example.laba17.domain.model.ProductFilter;

/**
 * Purpose: Collects and validates all Popular product filtering parameters.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: restoreFilter fills controls; applyFilter validates and returns parameters.
 */
public class FiltersActivity extends AppCompatActivity {

    private EditText minPrice;
    private EditText maxPrice;
    private Switch discountSwitch;
    private CheckBox black;
    private CheckBox white;
    private CheckBox blue;
    private CheckBox red;
    private CheckBox green;
    private CheckBox sport;
    private CheckBox casual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        minPrice = findViewById(R.id.minPrice);
        maxPrice = findViewById(R.id.maxPrice);
        discountSwitch = findViewById(R.id.discountSwitch);
        black = findViewById(R.id.colorBlack);
        white = findViewById(R.id.colorWhite);
        blue = findViewById(R.id.colorBlue);
        red = findViewById(R.id.colorRed);
        green = findViewById(R.id.colorGreen);
        sport = findViewById(R.id.categorySport);
        casual = findViewById(R.id.categoryCasual);

        restoreFilter(ProductFilterIntent.read(getIntent()));
        findViewById(R.id.applyFilterButton).setOnClickListener(view -> applyFilter());
        findViewById(R.id.filtersBackButton).setOnClickListener(view -> finish());
    }

    private void restoreFilter(ProductFilter filter) {
        if (filter.getMinPrice() != null) {
            minPrice.setText(String.valueOf(filter.getMinPrice().intValue()));
        }
        if (filter.getMaxPrice() != null) {
            maxPrice.setText(String.valueOf(filter.getMaxPrice().intValue()));
        }
        discountSwitch.setChecked(filter.isDiscountedOnly());
        black.setChecked(filter.getColors().contains("Black"));
        white.setChecked(filter.getColors().contains("White"));
        blue.setChecked(filter.getColors().contains("Blue"));
        red.setChecked(filter.getColors().contains("Red"));
        green.setChecked(filter.getColors().contains("Green"));
        sport.setChecked(filter.getCategories().contains("Sport"));
        casual.setChecked(filter.getCategories().contains("Casual"));
    }

    private void applyFilter() {
        Double from = parsePrice(minPrice);
        Double to = parsePrice(maxPrice);
        if (from == null && !minPrice.getText().toString().trim().isEmpty()
                || to == null && !maxPrice.getText().toString().trim().isEmpty()) {
            showError(R.string.invalid_price);
            return;
        }
        if (from != null && to != null && from > to) {
            showError(R.string.invalid_price_range);
            return;
        }

        ProductFilter filter = new ProductFilter();
        filter.setMinPrice(from);
        filter.setMaxPrice(to);
        filter.setDiscountedOnly(discountSwitch.isChecked());
        addChecked(filter, black, "Black", true);
        addChecked(filter, white, "White", true);
        addChecked(filter, blue, "Blue", true);
        addChecked(filter, red, "Red", true);
        addChecked(filter, green, "Green", true);
        addChecked(filter, sport, "Sport", false);
        addChecked(filter, casual, "Casual", false);

        Intent result = new Intent();
        ProductFilterIntent.write(result, filter);
        setResult(RESULT_OK, result);
        finish();
    }

    private Double parsePrice(EditText input) {
        String value = input.getText().toString().trim();
        if (value.isEmpty()) {
            return null;
        }
        try {
            double parsed = Double.parseDouble(value);
            return parsed >= 0 ? parsed : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private void addChecked(
            ProductFilter filter,
            CheckBox checkBox,
            String value,
            boolean color
    ) {
        if (checkBox.isChecked()) {
            if (color) {
                filter.getColors().add(value);
            } else {
                filter.getCategories().add(value);
            }
        }
    }

    private void showError(int message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.filter_error)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
