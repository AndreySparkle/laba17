package com.example.laba17.domain.manager;

import com.example.laba17.domain.model.OnboardingItem;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Purpose: Manages onboarding items in first-in, first-out order.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: addItem, getNextItem, getItemsCount, hasMultipleItems and
 * getIndicatorCount manage and describe the queue.
 */
public class OnboardingQueueManager {

    private final Queue<OnboardingItem> items = new LinkedList<>();

    public void addItem(OnboardingItem item) {
        if (item != null) {
            items.offer(item);
        }
    }

    public OnboardingItem getNextItem() {
        return items.poll();
    }

    public int getItemsCount() {
        return items.size();
    }

    public boolean hasMultipleItems() {
        return items.size() > 1;
    }

    public int getIndicatorCount() {
        return items.size();
    }
}
