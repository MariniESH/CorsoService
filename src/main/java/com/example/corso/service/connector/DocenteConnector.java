package com.example.corso.service.connector;

import com.example.corso.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
public class DocenteConnector {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public DocenteConnector(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient webClient() {
        // every time you build, the filter will inject the header
        return webClientBuilder.build();
    }

    public DocenteDTO getDocente(Long id) {
        return webClient().get()
                .uri("/docenti/{id}", id)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();
    }



}
