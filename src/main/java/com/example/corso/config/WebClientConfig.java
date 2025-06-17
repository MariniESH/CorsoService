package com.example.corso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .baseUrl("http://localhost:8050")
                .filter((request, next) -> {
                    String auth = HeaderCaptureFilter.getAuth();
                    if (auth != null) {
                        request = ClientRequest.from(request)
                                .header("Authorization", auth)
                                .build();
                    }
                    return next.exchange(request);
                });
    }
}
