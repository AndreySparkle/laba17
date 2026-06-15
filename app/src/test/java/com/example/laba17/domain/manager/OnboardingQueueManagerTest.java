package com.example.laba17.domain.manager;

import com.example.laba17.domain.model.OnboardingItem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Purpose: Verifies FIFO order, queue size changes and indicator count.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: tests cover item retrieval, removal and multiple indicators.
 */
public class OnboardingQueueManagerTest {

    private OnboardingQueueManager manager;
    private OnboardingItem first;
    private OnboardingItem second;
    private OnboardingItem third;

    @Before
    public void setUp() {
        manager = new OnboardingQueueManager();
        first = new OnboardingItem(1, "First", "First description");
        second = new OnboardingItem(2, "Second", "Second description");
        third = new OnboardingItem(3, "Third", "Third description");
    }

    @Test
    public void getNextItem_returnsItemsInOrderAdded() {
        manager.addItem(first);
        manager.addItem(second);
        manager.addItem(third);

        assertSame(first, manager.getNextItem());
        assertSame(second, manager.getNextItem());
        assertSame(third, manager.getNextItem());
    }

    @Test
    public void getNextItem_decreasesItemsCount() {
        manager.addItem(first);
        manager.addItem(second);

        assertEquals(2, manager.getItemsCount());
        manager.getNextItem();
        assertEquals(1, manager.getItemsCount());
    }

    @Test
    public void getIndicatorCount_matchesMultipleItems() {
        manager.addItem(first);
        manager.addItem(second);
        manager.addItem(third);

        assertTrue(manager.hasMultipleItems());
        assertEquals(3, manager.getIndicatorCount());
    }
}
