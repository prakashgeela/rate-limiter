package com.github.pgeela.ratelimiter.model;

public interface ExpirableState {
    boolean isExpired(long currentTime);
}
