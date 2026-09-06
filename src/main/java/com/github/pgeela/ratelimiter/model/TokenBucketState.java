package com.github.pgeela.ratelimiter.model;

public class TokenBucketState implements ExpirableState {

    private final long capacity;
    private final double refillRatePerMs;
    private  double tokenAvailable;
    private long lastRefillTimeStamp;

    public TokenBucketState(long capacity, double refillRatePerMs) {
        this.capacity = capacity;
        this.refillRatePerMs = refillRatePerMs;
        this.tokenAvailable = capacity;
        this.lastRefillTimeStamp = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume() {

        refill();
        if (tokenAvailable > 1.0) {
            tokenAvailable -= 1.0;
            return true;
        }

        return false;

    }

    private void refill() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefillTimeStamp;

        if (elapsedTime > 0) {
            this.tokenAvailable = Math.min((tokenAvailable + refillRatePerMs * elapsedTime),
                    capacity);
            this.lastRefillTimeStamp = currentTime;
        }
    }

    @Override
    public boolean isExpired(long currentTime) {
        long threshold = (long) Math.ceil(capacity / refillRatePerMs);
        return currentTime - lastRefillTimeStamp > threshold;
    }
}
