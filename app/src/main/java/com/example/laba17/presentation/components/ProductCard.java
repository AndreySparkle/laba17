package com.example.laba17.presentation.components;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.laba17.R;
import com.example.laba17.domain.model.Product;

import java.util.Locale;

/**
 * Purpose: Renders one reusable product card on every product list.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: bind displays a Product; setSelectedState highlights the current product.
 */
public class ProductCard extends FrameLayout {

    private final ImageView image;
    private final TextView discount;
    private final TextView title;
    private final TextView price;
    private final ImageButton favorite;
    private final ImageButton cart;

    public ProductCard(Context context) {
        this(context, null);
    }

    public ProductCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_product_card, this, true);
        image = findViewById(R.id.productImage);
        discount = findViewById(R.id.productDiscount);
        title = findViewById(R.id.productTitle);
        price = findViewById(R.id.productPrice);
        favorite = findViewById(R.id.favoriteButton);
        cart = findViewById(R.id.cartButton);
        setForeground(getResources().getDrawable(
                R.drawable.product_card_selector,
                context.getTheme()
        ));
    }

    public void bind(
            Product product,
            OnClickListener openListener,
            OnClickListener favoriteListener,
            OnClickListener cartListener
    ) {
        image.setImageResource(product.getImageResId());
        title.setText(product.getTitle());
        price.setText(String.format(Locale.getDefault(), "%.0f ₽", product.getPrice()));
        discount.setVisibility(product.getDiscount() > 0 ? VISIBLE : GONE);
        discount.setText(getResources().getString(
                R.string.discount_format,
                product.getDiscount()
        ));
        favorite.setImageResource(product.isFavorite()
                ? R.drawable.ic_favorite_filled
                : R.drawable.ic_favorite);
        favorite.setContentDescription(getResources().getString(product.isFavorite()
                ? R.string.remove_favorite
                : R.string.add_favorite));
        cart.setImageResource(product.isInCart()
                ? R.drawable.ic_cart_added
                : R.drawable.ic_cart);

        setOnClickListener(openListener);
        favorite.setOnClickListener(favoriteListener);
        cart.setOnClickListener(cartListener);
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
        setScaleX(selected ? 1f : 0.94f);
        setScaleY(selected ? 1f : 0.94f);
        setAlpha(selected ? 1f : 0.72f);
    }
}
