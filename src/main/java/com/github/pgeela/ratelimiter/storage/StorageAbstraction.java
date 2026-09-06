package com.github.pgeela.ratelimiter.storage;

import java.util.function.Function;

public interface StorageAbstraction<T> {
    T store(String key, Function<String, T> mappingFunction);
}
