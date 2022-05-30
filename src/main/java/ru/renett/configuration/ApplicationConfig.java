package ru.renett.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
