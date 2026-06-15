package com.example.laba17.domain;

/**
 * Purpose: Provides testable account session state.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: login activates the session; logout clears it; isLoggedIn reports state.
 */
public class SessionRepository {

    private boolean loggedIn;

    public SessionRepository(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void login() {
        loggedIn = true;
    }

    public void logout() {
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
