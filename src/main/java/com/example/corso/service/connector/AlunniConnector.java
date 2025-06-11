package com.example.corso.service.connector;

import com.example.corso.dto.AlunnoWithoutCorsiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.List;

@Component
public class AlunniConnector {
    private String credentials = "user:pass1234";
    private String encodedAuth = Base64.getEncoder().encodeToString(credentials.getBytes());

    @Autowired
    WebClient webClient;

    public AlunnoWithoutCorsiDTO getAlunno(Long id) {
        return webClient.get()
                .uri("/alunni/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .retrieve()
                .bodyToMono(AlunnoWithoutCorsiDTO.class)
                .block();
    }
}
