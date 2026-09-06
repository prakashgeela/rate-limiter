package com.github.pgeela.ratelimiter.algorithm;

import com.github.pgeela.ratelimiter.model.TokenBucketState;
import com.github.pgeela.ratelimiter.storage.InMemoryStorage;
import com.github.pgeela.ratelimiter.storage.StorageAbstraction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private final long capacity;
    private final double refillRatePerMs;
    private final Map<String, TokenBucketState> userToTokenMap = new ConcurrentHashMap<>();
    private final StorageAbstraction<TokenBucketState> storage;

    public TokenBucketAlgorithm(long capacity, double refillRatePerMs, StorageAbstraction<TokenBucketState> storage) {

        if (capacity <= 0 || refillRatePerMs <= 0) {
            throw new IllegalArgumentException("Capacity and refill rate must be positive");
        }

        this.capacity = capacity;
        this.refillRatePerMs = refillRatePerMs;
        this.storage = storage;
    }

    @Override
    public boolean isAllowed(String userId) {
        TokenBucketState tokenBucketState = storage.store(
                userId, k -> new TokenBucketState(capacity, refillRatePerMs));
        return tokenBucketState.tryConsume();
    }
}
