package com.github.pgeela.ratelimiter.storage;


import com.github.pgeela.ratelimiter.model.ExpirableState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class InMemoryStorage<T extends ExpirableState> implements StorageAbstraction<T> {

    private final Map<String, T> map = new ConcurrentHashMap<>();
    ScheduledExecutorService cleaningService;

    public InMemoryStorage() {

        ThreadFactory factory = runnable -> {
            Thread thread = new Thread();
            thread.setDaemon(true);
            return thread;
        };
        cleaningService = Executors.newSingleThreadScheduledExecutor(factory);

        // Schedule periodic cleanup
        this.cleaningService.scheduleAtFixedRate(
                this::evictIfEligible,
                60,
                60,
                TimeUnit.SECONDS
        );

    }


    private void evictIfEligible() {

        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (entry.getValue().isExpired(System.currentTimeMillis())) {
                map.remove(entry.getKey());
            }
        }
    }


    @Override
    public T store(String key, Function<String, T> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }
}
