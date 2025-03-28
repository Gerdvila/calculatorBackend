package com.leasing.calculator.util;

import java.time.Instant;

public record CarAPIJwt(String jwt, int expiresAt) {

    public boolean isExpired() {
        long currentTime = Instant.now().getEpochSecond();
        return currentTime > expiresAt;
    }
}