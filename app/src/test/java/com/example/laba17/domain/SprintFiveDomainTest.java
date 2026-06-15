package com.example.laba17.domain;

import com.example.laba17.common.OrderDateFormatter;
import com.example.laba17.domain.model.Order;
import com.example.laba17.domain.model.OrderItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Purpose: Verifies Sprint 5 logout, order actions, grouping and date formatting.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: tests cover all required TDD scenarios.
 */
public class SprintFiveDomainTest {

    private long now;
    private OrderRepository repository;

    @Before
    public void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.JUNE, 15, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        now = calendar.getTimeInMillis();

        List<OrderItem> items = Arrays.asList(new OrderItem(1, "Air Run", 2, 1000));
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, now - 10 * 60_000L, "Delivered", items));
        orders.add(new Order(2, now - 24 * 60 * 60_000L, "Processing", items));
        repository = new OrderRepository(orders);
    }

    @Test
    public void logout_clearsSession() {
        SessionRepository session = new SessionRepository(true);

        session.logout();

        assertFalse(session.isLoggedIn());
    }

    @Test
    public void repeatOrder_createsNewOrderWithSameItems() {
        Order repeated = repository.repeatOrder(1, now);

        assertNotNull(repeated);
        assertEquals(3, repeated.getId());
        assertEquals(2, repeated.getItems().get(0).getQuantity());
        assertEquals(OrderRepository.STATUS_CREATED, repeated.getStatus());
    }

    @Test
    public void cancelOrder_changesStatus() {
        repository.cancelOrder(2);

        assertEquals(OrderRepository.STATUS_CANCELLED, repository.findById(2).getStatus());
    }

    @Test
    public void groupByDate_createsSeparateDateGroups() {
        Map<String, List<Order>> groups = repository.groupByDate(Locale.US);

        assertEquals(2, groups.size());
    }

    @Test
    public void formatDate_usesMinutesForRecentAndDateForOlderOrder() {
        assertEquals("10 min ago", OrderDateFormatter.format(
                now - 10 * 60_000L,
                now,
                Locale.US
        ));
        assertEquals("14.06.2026", OrderDateFormatter.format(
                now - 24 * 60 * 60_000L,
                now,
                Locale.US
        ));
    }
}
