package com.github.pgeela.ratelimiter.factory;

import com.github.pgeela.ratelimiter.config.RateLimitConfig;
import com.github.pgeela.ratelimiter.storage.InMemoryStorage;
import com.github.pgeela.ratelimiter.storage.StorageAbstraction;

import static com.github.pgeela.ratelimiter.util.Constants.DEFAULT_RATE_LIMIT_STORAGE_TYPE;
import static com.github.pgeela.ratelimiter.util.Constants.RATE_LIMIT_STORAGE_TYPE;

public class StorageFactory {



    public static <T> StorageAbstraction <T> createAndGetStorage(RateLimitConfig config) {
        String storageType = config.getValue(RATE_LIMIT_STORAGE_TYPE, DEFAULT_RATE_LIMIT_STORAGE_TYPE);

        switch (storageType) {
            case DEFAULT_RATE_LIMIT_STORAGE_TYPE:
            default:
                return new InMemoryStorage();
        }
    }
}
