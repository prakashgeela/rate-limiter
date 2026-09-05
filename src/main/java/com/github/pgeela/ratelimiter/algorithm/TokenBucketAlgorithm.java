package com.github.pgeela.ratelimiter.algorithm;

import com.github.pgeela.ratelimiter.model.TokenBucketState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private long capacity;
    private double refillRatePerMs;

    public TokenBucketAlgorithm(long capacity, double refillRatePerMs) {
        this.capacity = capacity;
        this.refillRatePerMs = refillRatePerMs;
    }

    private Map<String, TokenBucketState> userToTokenMap = new ConcurrentHashMap<>();
    @Override
    public boolean isAllowed(String userId) {
        TokenBucketState tokenBucketState = userToTokenMap.getOrDefault(
                userId, new TokenBucketState(capacity, refillRatePerMs));

        return tokenBucketState.tryConsume();
    }
}
