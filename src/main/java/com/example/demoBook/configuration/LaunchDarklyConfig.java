package com.example.demoBook.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.launchdarkly.sdk.server.Components;
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;

@Configuration
public class LaunchDarklyConfig {
    @Value("${launch-darkly.sdk-key}")
    private String launchDarklyKey;

    @Bean
    public LDConfig configLaunch() {
        return new LDConfig.Builder()
                .http(
                        Components.httpConfiguration()
                                .connectTimeout(Duration.ofSeconds(5))
                                .socketTimeout(Duration.ofSeconds(5))
                )
                .events(
                        Components.sendEvents()
                                .flushInterval(Duration.ofSeconds(10))
                )
                .build();
    }

    @Bean
    public LDClient ldClient() {
        return new LDClient(launchDarklyKey, configLaunch());
    }
}
