package com.github.pgeela.ratelimiter.factory;

import com.github.pgeela.ratelimiter.algorithm.RateLimitAlgorithm;
import com.github.pgeela.ratelimiter.algorithm.TokenBucketAlgorithm;
import com.github.pgeela.ratelimiter.config.RateLimitConfig;

import static com.github.pgeela.ratelimiter.util.Constants.DEFAULT_RATE_LIMITER_TOKEN_BUCKET_CAPACITY;
import static com.github.pgeela.ratelimiter.util.Constants.DEFAULT_RATE_LIMITER_TOKEN_BUCKET_REFILL_RATE_PER_MS;
import static com.github.pgeela.ratelimiter.util.Constants.RATE_LIMITER_STRATEGY;
import static com.github.pgeela.ratelimiter.util.Constants.RATE_LIMITER_TOKEN_BUCKET_CAPACITY;
import static com.github.pgeela.ratelimiter.util.Constants.RATE_LIMITER_TOKEN_BUCKET_REFILL_RATE_PER_MS;
import static com.github.pgeela.ratelimiter.util.Constants.TOKEN_BUCKET;

public class RateLimitAlgoFactory {

    public static RateLimitAlgorithm createAlgo(RateLimitConfig config) {



        String algoType = config.getValue(RATE_LIMITER_STRATEGY) != null
                ? config.getValue(RATE_LIMITER_STRATEGY) : TOKEN_BUCKET;

        switch (algoType) {

            case TOKEN_BUCKET:
                String capacityInStr = config.getValue(RATE_LIMITER_TOKEN_BUCKET_CAPACITY)
                        != null ? config.getValue(RATE_LIMITER_TOKEN_BUCKET_CAPACITY) : "100";
                long capacity = Long.valueOf(capacityInStr);
                String refillInStr = config.getValue(RATE_LIMITER_TOKEN_BUCKET_REFILL_RATE_PER_MS)
                        != null ? config.getValue(RATE_LIMITER_TOKEN_BUCKET_REFILL_RATE_PER_MS) : "0.1";
                double refillInMs = Double.valueOf(refillInStr);

                return new TokenBucketAlgorithm(capacity, refillInMs);

            default:
                return new TokenBucketAlgorithm(Long.valueOf(DEFAULT_RATE_LIMITER_TOKEN_BUCKET_CAPACITY),
                        Double.valueOf(DEFAULT_RATE_LIMITER_TOKEN_BUCKET_REFILL_RATE_PER_MS));
        }


    }
}
