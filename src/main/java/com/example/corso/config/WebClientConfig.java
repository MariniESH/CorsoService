package com.example.corso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${Basic-Auth-User}") String username,
                               @Value("${Basic-Auth-Password}") String password) {
        String credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + credentials)
                .baseUrl("http://localhost:8050").build();
    }


}
