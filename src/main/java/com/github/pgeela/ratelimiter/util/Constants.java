package com.github.pgeela.ratelimiter.util;

public class Constants {

    public static final String TOKEN_BUCKET = "token_bucket";
    public static final String RATE_LIMITER_STRATEGY = "rate_limiter.strategy";
    public static final String RATE_LIMITER_TOKEN_BUCKET_CAPACITY = "rate_limiter_token_bucket_capacity";
    public static final String RATE_LIMITER_TOKEN_BUCKET_REFILL_RATE_PER_MS = "rate_limiter_token_bucket_refill_rate_per_ms";
    public static final String DEFAULT_RATE_LIMITER_TOKEN_BUCKET_CAPACITY = "100";
    public static final String DEFAULT_RATE_LIMITER_TOKEN_BUCKET_REFILL_RATE_PER_MS = "0.1";
    public static final String RATE_LIMIT_STORAGE_TYPE = "rate_limit_storage_type";
    public static final String DEFAULT_RATE_LIMIT_STORAGE_TYPE = "IN-MEMORY";
}
