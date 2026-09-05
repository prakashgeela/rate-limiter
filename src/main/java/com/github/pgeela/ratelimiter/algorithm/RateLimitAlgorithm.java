package com.github.pgeela.ratelimiter.algorithm;

public interface RateLimitAlgorithm {

    boolean isAllowed(String userId);
}
