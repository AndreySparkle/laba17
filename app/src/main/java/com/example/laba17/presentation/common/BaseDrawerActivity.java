package com.example.laba17.presentation.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.laba17.R;
import com.example.laba17.data.LocalSessionRepository;
import com.example.laba17.presentation.cart.CartActivity;
import com.example.laba17.presentation.catalog.CatalogActivity;
import com.example.laba17.presentation.favorite.FavoriteActivity;
import com.example.laba17.presentation.home.HomeActivity;
import com.example.laba17.presentation.notifications.NotificationActivity;
import com.example.laba17.presentation.orders.OrdersActivity;
import com.example.laba17.presentation.popular.PopularActivity;
import com.example.laba17.presentation.profile.ProfileActivity;
import com.example.laba17.presentation.signin.SignInActivity;
import com.google.android.material.navigation.NavigationView;

/**
 * Purpose: Adds one reusable Navigation Drawer to all main shop screens.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: setContentView wraps screen content; openDestination navigates; logout clears session.
 */
public abstract class BaseDrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        drawerLayout = new DrawerLayout(this);
        drawerLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        FrameLayout content = new FrameLayout(this);
        content.setLayoutParams(new DrawerLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        LayoutInflater.from(this).inflate(layoutResID, content, true);
        drawerLayout.addView(content);

        ImageButton menuButton = new ImageButton(this);
        menuButton.setImageResource(R.drawable.ic_menu);
        menuButton.setBackgroundResource(R.drawable.drawer_button_background);
        menuButton.setContentDescription(getString(R.string.open_menu));
        int size = getResources().getDimensionPixelSize(R.dimen.drawer_button_size);
        FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(size, size);
        buttonParams.gravity = Gravity.START | Gravity.TOP;
        int margin = getResources().getDimensionPixelSize(R.dimen.drawer_button_margin);
        buttonParams.setMargins(margin, margin, margin, margin);
        content.addView(menuButton, buttonParams);
        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        NavigationView navigation = new NavigationView(this);
        DrawerLayout.LayoutParams navigationParams = new DrawerLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.drawer_width),
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        navigationParams.gravity = GravityCompat.START;
        navigation.setLayoutParams(navigationParams);
        navigation.inflateHeaderView(R.layout.drawer_header);
        navigation.inflateMenu(R.menu.menu_navigation_drawer);
        navigation.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            if (item.getItemId() == R.id.drawer_logout) {
                logout();
            } else {
                openDestination(item.getItemId());
            }
            return true;
        });
        drawerLayout.addView(navigation);
        super.setContentView(drawerLayout);
    }

    private void openDestination(int itemId) {
        Class<? extends AppCompatActivity> destination;
        if (itemId == R.id.drawer_home) {
            destination = HomeActivity.class;
        } else if (itemId == R.id.drawer_catalog) {
            destination = CatalogActivity.class;
        } else if (itemId == R.id.drawer_popular) {
            destination = PopularActivity.class;
        } else if (itemId == R.id.drawer_favorite) {
            destination = FavoriteActivity.class;
        } else if (itemId == R.id.drawer_cart) {
            destination = CartActivity.class;
        } else if (itemId == R.id.drawer_profile) {
            destination = ProfileActivity.class;
        } else if (itemId == R.id.drawer_orders) {
            destination = OrdersActivity.class;
        } else {
            destination = NotificationActivity.class;
        }
        if (!destination.isInstance(this)) {
            Intent intent = new Intent(this, destination);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    private void logout() {
        new LocalSessionRepository(this).logout();
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
