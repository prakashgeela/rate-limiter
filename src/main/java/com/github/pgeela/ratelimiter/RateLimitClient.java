package com.github.pgeela.ratelimiter;

import com.github.pgeela.ratelimiter.algorithm.RateLimitAlgorithm;
import com.github.pgeela.ratelimiter.config.RateLimitConfig;
import com.github.pgeela.ratelimiter.factory.RateLimitAlgoFactory;

import java.util.Properties;

public class RateLimitClient {

    private RateLimitConfig rateLimitConfig;
    private RateLimitAlgorithm rateLimitAlgorithm;

    public RateLimitClient() {
        this(new Properties());
    }

    public RateLimitClient(Properties overrideProperties) {
        rateLimitConfig = new RateLimitConfig(overrideProperties);
        rateLimitAlgorithm = RateLimitAlgoFactory.createAlgo(rateLimitConfig);
    }

    public boolean isAllowed(String userId) {
        return rateLimitAlgorithm.isAllowed(userId);
    }





}
