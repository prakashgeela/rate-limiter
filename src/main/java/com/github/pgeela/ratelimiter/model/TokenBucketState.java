package com.github.pgeela.ratelimiter.model;

public class TokenBucketState {

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
        if (tokenAvailable > 1) {
            tokenAvailable--;
            return true;
        }

        return false;

    }

    public void refill() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefillTimeStamp;

        if (elapsedTime > 0) {
            this.tokenAvailable = Math.min((tokenAvailable + refillRatePerMs * elapsedTime),
                    capacity);
            this.lastRefillTimeStamp = currentTime;
        }


    }


}
