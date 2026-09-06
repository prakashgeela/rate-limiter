package com.github.pgeela.ratelimiter.algorithm;

import com.github.pgeela.ratelimiter.model.TokenBucketState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private final long capacity;
    private final double refillRatePerMs;
    private final Map<String, TokenBucketState> userToTokenMap = new ConcurrentHashMap<>();

    public TokenBucketAlgorithm(long capacity, double refillRatePerMs) {

        if (capacity <= 0 || refillRatePerMs <= 0) {
            throw new IllegalArgumentException("Capacity and refill rate must be positive");
        }

        this.capacity = capacity;
        this.refillRatePerMs = refillRatePerMs;
    }

    @Override
    public boolean isAllowed(String userId) {
        TokenBucketState tokenBucketState = userToTokenMap.computeIfAbsent(
                userId, k -> new TokenBucketState(capacity, refillRatePerMs));

        return tokenBucketState.tryConsume();
    }
}
