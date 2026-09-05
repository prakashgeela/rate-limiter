package com.github.pgeela.ratelimiter.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RateLimitConfig {

    String DeFAULT_CONFIG_FILE = "default-ratelimiter.properties";
    Properties properties = new Properties();

    public RateLimitConfig() {
        this(null);
    }

    public RateLimitConfig(Properties properties) {
        setDefaultProperties();
        setOverrideProperties(properties);

    }

    private void setDefaultProperties() {

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(DeFAULT_CONFIG_FILE)) {

            if (stream != null) {
                properties.load(stream);
            } else {
                System.out.println("Nothing found at default path");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOverrideProperties(Properties overrideProperties) {
        if (overrideProperties == null || overrideProperties.isEmpty()) return;
        properties.putAll(overrideProperties);
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
